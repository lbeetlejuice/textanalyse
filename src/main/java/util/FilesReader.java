package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.DataStorage;
import model.IndexElement;
import model.MetadataDocument;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Files Reader - Utility class which reads and tokenizes files
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class FilesReader {
	private DataStorage data;

	public FilesReader(DataStorage data) {
		this.data = data;
	}

	/**
	 * Populates the given list with all files from the given folder
	 * 
	 * @param folderName Path to the folder which contains the review files
	 * @param documentsList The list which should be populated with all files from the review files folder
	 */
	public void populateDocumentListWithFiles(String folderName) {
		try {
			Path startPath = Paths.get(folderName);
			Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path filePath, BasicFileAttributes attributes) {
					data.getDocuments().add(new MetadataDocument(filePath, attributes.size()));
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads all files and tokenizes words
	 */
	public void readAndTokenizeAllFiles() {
		Pattern scanPattern = Pattern.compile("\\S+");
		Pattern replacePattern = Pattern.compile("\\p{Punct}");

		try {
			for (MetadataDocument document : data.getDocuments()) {
				BufferedReader reader = new BufferedReader(new FileReader(document.getFilePath().toFile()));

				String content = reader.readLine();
				Matcher matcher = scanPattern.matcher(content);

				int wordCounter = 0;
				int characterCounter = 0;

				while (matcher.find()) {
					int startPosition = matcher.start();
					int endPosition = matcher.end();
					String token = matcher.group();

					characterCounter = startPosition + token.length();
					token = replacePattern.matcher(token).replaceAll("").toLowerCase();

					if (addWord(token, document, CantorPairing.cantorize(startPosition, endPosition))) {
						wordCounter++;
					}
				}

				document.setCountWords(wordCounter);
				document.setCountCharacters(characterCounter);
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a word do the search index and word sizes list
	 * 
	 * @param word The word which is added to the search index
	 * @param document The document which contains the word
	 * @param position The position of the word in the element
	 * @return True if the word has been added to the search index; false otherwise
	 */
	public boolean addWord(String word, MetadataDocument document, int position) {
		if (word.length() == 0) {
			return false;
		}

		if (data.getSearchIndex().containsKey(word)) {
			data.getSearchIndex().get(word).addPosition(document, position);
		}
		else {
			data.getSearchIndex().put(word, new IndexElement(document, position));
		}

		data.getWordSize().get(word.length()).add(word);

		return true;
	}

	/**
	 * Populates the list of the most used words
	 * 
	 * @param numberOfEntries The number of list entries
	 */
	public void processTopList(int numberOfEntries) {
		HashMap<String, IndexElement> searchIndex = data.getSearchIndex();
		TreeMap<Integer, String> topList = data.getTopList();

		for (Map.Entry<String, IndexElement> entry : searchIndex.entrySet()) {
			String word = entry.getKey();
			int numberOfPositions = entry.getValue().getNumberOfPositions();

			if (!data.getStopWordList().contains(word)) {
				topList.put(numberOfPositions, word);

				if (topList.size() >= numberOfEntries) {
					topList.remove(Collections.min(topList.keySet()));
				}
			}
		}
	}

}
