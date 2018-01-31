package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import util.CantorPairing;
import util.TagConverter;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Metadata Document - Stores the metadata about a single file
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class MetadataDocument {
	private Path filePath;
	private long fileSizeByte;
	private int countWords;
	private int countCharacters;

	/**
	 * Constructor
	 * 
	 * @param filePath The path to the file
	 * @param fileSizeByte The file size in bytes
	 */
	public MetadataDocument(Path filePath, long fileSizeByte) {
		this.filePath = filePath;
		this.fileSizeByte = fileSizeByte;
	}

	public int getCountWords() {
		return countWords;
	}

	public void setCountWords(int countWords) {
		this.countWords = countWords;
	}

	public int getCountCharacters() {
		return countCharacters;
	}

	public void setCountCharacters(int countCharacters) {
		this.countCharacters = countCharacters;
	}

	public Path getFilePath() {
		return filePath;
	}

	public long getFileSizeByte() {
		return fileSizeByte;
	}

	public String getAuthor() {
		return filePath.getName(filePath.getNameCount() - 2).toString();
	}

	public String getFileName() {
		String fileName = filePath.getFileName().toString();
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	public String getContent(ArrayList<Integer> highlightPositions) {
		String content = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()));
			content = reader.readLine();

			// Sort positions
			TreeMap<Integer, Integer> sortedPositions = new TreeMap<>();
			for (int position : highlightPositions) {
				int startPosition = CantorPairing.cantor1(position);
				int endPosition = CantorPairing.cantor2(position);
				sortedPositions.put(startPosition, endPosition);
			}

			// Highlight positions
			int correctionFactor = 0;
			for (Map.Entry<Integer, Integer> entry : sortedPositions.entrySet()) {
				int startPosition = entry.getKey();
				int endPosition = entry.getValue();

				content = content.substring(0, startPosition + correctionFactor) + "[highlight]"
						+ content.substring(startPosition + correctionFactor, endPosition + correctionFactor)
						+ "[/highlight]" + content.substring(endPosition + correctionFactor);

				correctionFactor += "[highlight][/highlight]".length();
			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return TagConverter.convertTags(content);
	}

	@Override
	public String toString() {
		return "MetadataDocument [filePath=" + filePath + "]";
	}

}
