package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Index Element - stores a document with a list of positions
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class IndexElement {
	private HashMap<MetadataDocument, ArrayList<Integer>> elements;

	/**
	 * Constructor
	 */
	public IndexElement() {
		elements = new HashMap<>();
	}

	/**
	 * Constructor
	 * 
	 * @param document The document where the word is found
	 * @param position The position of the word in the document
	 */
	public IndexElement(MetadataDocument document, int position) {
		elements = new HashMap<>();
		ArrayList<Integer> positionsList = new ArrayList<>();
		positionsList.add(position);
		elements.put(document, positionsList);
	}

	/**
	 * Adds a position to an existing element
	 * 
	 * @param document The document where the word is found
	 * @param position The position of the word in the document
	 */
	public void addPosition(MetadataDocument document, int position) {
		if (elements.containsKey(document)) {
			elements.get(document).add(position);
		}
		else {
			ArrayList<Integer> positionsList = new ArrayList<>();
			positionsList.add(position);
			elements.put(document, positionsList);
		}
	}

	public HashMap<MetadataDocument, ArrayList<Integer>> getElements() {
		return elements;
	}

	public Set<MetadataDocument> getAllDocuments() {
		return elements.keySet();
	}

	public int getNumberOfPositions() {
		int numberOfPositions = 0;

		for (ArrayList<Integer> positionsList : elements.values()) {
			numberOfPositions += positionsList.size();
		}

		return numberOfPositions;
	}

	@Override
	public String toString() {
		return "IndexElement [elements=" + elements + "]";
	}

	public IndexElement getCopyOf() {
		IndexElement copy = new IndexElement();
		copy.elements = new HashMap<>();
		for (MetadataDocument document : elements.keySet()) {
			for (Integer position : elements.get(document)) {
				copy.addPosition(document, position);
			}
		}
		return copy;
	}

	public ArrayList<Integer> gePositions(MetadataDocument document) {
		return elements.get(document);
	}

	public void addAllPositions(MetadataDocument document, ArrayList<Integer> positions) {
		if (elements.containsKey(document)) {
			elements.get(document).addAll(positions);
		}
		else {
			ArrayList<Integer> positionsList = new ArrayList<>();
			positionsList.addAll(positions);
			elements.put(document, positionsList);
		}
	}

	public void addIndexElement(IndexElement newElement) {
		HashMap<MetadataDocument, ArrayList<Integer>> newElements = newElement.elements;
		Set<MetadataDocument> newDocuments = newElements.keySet();

		for (MetadataDocument document : newDocuments) {
			addAllPositions(document, newElements.get(document));
		}
	}

}
