package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import model.IndexElement;
import model.MetadataDocument;
import util.FilesReader;
import util.Levenshtein;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Main Controller
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class MainController {
	private final static int MAX_LEVENSHTEIN_DISTANCE = 3;
	private DataStorage data;

	/**
	 * Main method for testing purposes
	 * 
	 * @param args Path to the folder which contains the review files
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Please pass a valid path to the review files folder.");
		}
		else {
			@SuppressWarnings("unused")
			MainController mainController = new MainController(args[0]);
		}
	}

	/**
	 * Constructor
	 * 
	 * @param folderName Path to the folder which contains the review files
	 */
	public MainController(String folderName) {
		data = new DataStorage();
		FilesReader files = new FilesReader(data);

		files.populateDocumentListWithFiles(folderName);
		files.readAndTokenizeAllFiles();
		files.processTopList(50);
	}

	public IndexElement searchMultipleWordsOR(ArrayList<String> inDocument, ArrayList<String> notInDocument) {
		ArrayList<ArrayList<String>> inDocumentParts = new ArrayList<>();
		ArrayList<ArrayList<String>> notInDocumentParts = new ArrayList<>();
		IndexElement indexElement = new IndexElement();
		
		ArrayList<String> temp = new ArrayList<>();
		for (String word : inDocument) {
			if ("OR".equals(word)) {
				inDocumentParts.add(temp);
				temp = new ArrayList<>();
			}
			else {
				temp.add(word);
			}
		}
		inDocumentParts.add(temp);
		temp = new ArrayList<>();

		for (String word : notInDocument) {
			if ("OR".equals(word)) {
				notInDocumentParts.add(temp);
				temp = new ArrayList<>();
			}
			else {
				temp.add(word);
			}
		}
		notInDocumentParts.add(temp);

		if (inDocumentParts.size() == notInDocumentParts.size()) {
			for (int i = 0; i < inDocumentParts.size(); i++) {
				indexElement.addIndexElement(searchMultipleWords(inDocumentParts.get(i), notInDocumentParts.get(i)));
			}
		}
		
		return indexElement;
	}

	public IndexElement searchMultipleWords(ArrayList<String> inDocument, ArrayList<String> notInDocument) {
		if (inDocument == null || inDocument.size() == 0) {
			throw new IllegalArgumentException();
		}

		HashMap<String, IndexElement> searchIndex = data.getSearchIndex();
		IndexElement indexElement;
		String firstWord = null;
		Set<MetadataDocument> documents = null;
		boolean hasNotInDocumentWords = !(notInDocument == null || notInDocument.size() == 0);

		do {
			int lastElement = inDocument.size() - 1;
			String word = findBestMatch(inDocument.get(lastElement));
			inDocument.remove(lastElement);
			if (!word.equals("")) {
				firstWord = word;
			}
		} while (firstWord == null && inDocument.size() > 0);

		if ((firstWord == null)) {
			indexElement = new IndexElement();
		}
		else {

			indexElement = data.getSearchIndex().get(firstWord).getCopyOf();
			documents = indexElement.getAllDocuments();

			for (String word : inDocument) {
				word = findBestMatch(word);
				if (!"".equals(word)) {
					searchWordAND(documents, word);
					addPositions(indexElement, searchIndex.get(word));
				}
			}

			if (hasNotInDocumentWords) {
				for (String word : notInDocument) {
					word = findBestMatch(word);
					if (!"".equals(word)) {
						searchWordNOT(documents, word);
					}
				}
			}
		}

		return indexElement;
	}

	private void addPositions(IndexElement indexElement, IndexElement toAdd) {
		Set<MetadataDocument> documents = indexElement.getAllDocuments();
		for (MetadataDocument document : documents) {
			ArrayList<Integer> positions = toAdd.gePositions(document);
			indexElement.addAllPositions(document, positions);
		}

	}

	private String findBestMatch(String searchWord) {
		String bestWord = "";

		if (data.getSearchIndex().containsKey(searchWord)) {
			return searchWord;
		}

		ArrayList<HashSet<String>> wordSize = data.getWordSize();
		int bestMatch = Integer.MAX_VALUE;
		int lowerBound = Math.max(searchWord.length() - MAX_LEVENSHTEIN_DISTANCE, 0);
		int upperBound = Math.min(searchWord.length() + MAX_LEVENSHTEIN_DISTANCE + 1, wordSize.size());

		for (int i = lowerBound; i < upperBound; i++) {
			for (String word : wordSize.get(i)) {
				int levenshteinDistance = Levenshtein.damerauLevenshteinDistance(searchWord, word);
				if (levenshteinDistance < bestMatch) {
					bestWord = word;
					bestMatch = levenshteinDistance;
				}
			}
		}

		return bestWord;
	}

	private void searchWordAND(Set<MetadataDocument> documents, String newWord) {
		Set<MetadataDocument> mewDocuments = data.getSearchIndex().get(newWord).getAllDocuments();

		Iterator<MetadataDocument> docs = documents.iterator();
		while (docs.hasNext()) {
			if (!mewDocuments.contains(docs.next())) {
				docs.remove();
			}
		}
	}

	private void searchWordNOT(Set<MetadataDocument> documents, String newWord) {
		Set<MetadataDocument> mewDocuments = data.getSearchIndex().get(newWord).getAllDocuments();

		Iterator<MetadataDocument> docs = documents.iterator();
		while (docs.hasNext()) {
			if (mewDocuments.contains(docs.next())) {
				docs.remove();
			}
		}
	}

	/**
	 * Searches a given word in the search index
	 * 
	 * @param word The word which should be found in the search index
	 * @return An index element (document and position)
	 */
	public IndexElement searchWordExact(String word) {
		IndexElement element = data.getSearchIndex().get(word);

		if (element == null) {
			element = new IndexElement();
		}

		return element;
	}

	/**
	 * Searches a given word in the search index using the levenshtein distance
	 * 
	 * @param word The word which should be found in the search index
	 * @return A collection with index elements (document and position)
	 */
	public IndexElement searchWord(String word) {
		return searchWordExact(findBestMatch(word));
	}

	public int getNumberOfDocuments() {
		return data.getDocuments().size();
	}

	public int getNumberOfDifferentWords() {
		return data.getSearchIndex().size();
	}

	public int getNumberOfWords() {
		int numberOfWords = 0;

		for (IndexElement entry : data.getSearchIndex().values()) {
			numberOfWords += entry.getNumberOfPositions();
		}

		return numberOfWords;
	}

	public long getTotalSizeOfDocumentsInBytes() {
		long totalSize = 0;

		for (MetadataDocument document : data.getDocuments()) {
			totalSize += document.getFileSizeByte();
		}

		return totalSize;
	}

	public String getTotalSizeOfDocuments() {
		long totalSize = getTotalSizeOfDocumentsInBytes();

		if (totalSize < 1024) {
			return totalSize + " Bytes";
		}
		if (totalSize < 1048576) {
			return String.format("%.2f", (double) totalSize / 1024) + " KB";
		}

		return String.format("%.2f", (double) totalSize / 1024 / 1024) + " MB";
	}

	public TreeMap<Integer, String> getTopList() {
		return data.getTopList();
	}
}
