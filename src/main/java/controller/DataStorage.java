package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import model.IndexElement;
import model.MetadataDocument;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Data Storage - Stores all data
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class DataStorage {
	private final static int MAX_WORD_LENGTH = 100;
	private ArrayList<MetadataDocument> documents;
	private HashMap<String, IndexElement> searchIndex;
	private ArrayList<HashSet<String>> wordSize;
	private TreeMap<Integer, String> topList;
	private ArrayList<String> stopWordList;

	public DataStorage() {
		documents = new ArrayList<>();
		searchIndex = new HashMap<>();
		wordSize = new ArrayList<>();
		topList = new TreeMap<>(Collections.reverseOrder());
		stopWordList = new ArrayList<>();

		// Initialize word list
		for (int counter = 0; counter <= MAX_WORD_LENGTH; counter++) {
			wordSize.add(counter, new HashSet<String>());
		}

		// Fill stop word list
		Collections.addAll(stopWordList, "a", "able", "about", "across", "after", "all", "almost", "also", "am",
				"among", "an", "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot",
				"could", "dear", "did", "do", "does", "either", "else", "ever", "every", "for", "from", "get", "got",
				"had", "has", "have", "he", "her", "hers", "him", "his", "how", "however", "i", "if", "in", "into",
				"is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might", "most", "must", "my",
				"neither", "no", "nor", "not", "of", "off", "often", "on", "only", "or", "other", "our", "own",
				"rather", "said", "say", "says", "she", "should", "since", "so", "some", "than", "that", "the", "their",
				"them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas", "us", "wants", "was",
				"we", "were", "what", "when", "where", "which", "while", "who", "whom", "why", "will", "with", "would",
				"yet", "you", "your");
	}

	public ArrayList<MetadataDocument> getDocuments() {
		return documents;
	}

	public HashMap<String, IndexElement> getSearchIndex() {
		return searchIndex;
	}

	public ArrayList<HashSet<String>> getWordSize() {
		return wordSize;
	}

	public TreeMap<Integer, String> getTopList() {
		return topList;
	}

	public ArrayList<String> getStopWordList() {
		return stopWordList;
	}

}
