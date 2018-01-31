package util;

import java.util.HashMap;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * TagConverter
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160523
 */
public class TagConverter {

	/**
	 * Do the full conversion from the Highlighting-Tag to HTML-Tags with cleaning input first
	 *
	 * @param input The text with the Highlighting-Tag
	 * @return HTML containing converted Tags
	 */
	public static String convertTags(String input) {
		if (input == null) {
			input = "";
		}

		input = cleanInput(input);
		input = parseTags(input);

		return input;
	}

	/**
	 * Cleans text from evil tags / bad words
	 *
	 * @param input Text with possible evil tags / bad words
	 * @return Cleaned text
	 */
	public static String cleanInput(String input) {
		if (input == null) {
			input = "";
		}

		HashMap<String, String> illegalCharacterMapFirst = new HashMap<String, String>();
		illegalCharacterMapFirst.put("&", "&amp;");

		HashMap<String, String> illegalCharacterMapLast = new HashMap<String, String>();
		illegalCharacterMapLast.put("<", "&lt;");
		illegalCharacterMapLast.put(">", "&gt;");
		illegalCharacterMapLast.put("#", "&#35;");
		illegalCharacterMapLast.put("\"", "&quot;");

		for (HashMap.Entry<String, String> entry : illegalCharacterMapFirst.entrySet()) {
			input = input.replaceAll(entry.getKey(), entry.getValue());
		}

		for (HashMap.Entry<String, String> entry : illegalCharacterMapLast.entrySet()) {
			input = input.replaceAll(entry.getKey(), entry.getValue());
		}

		return input;
	}

	/**
	 * Parses the Highlighting-Tag to HTML
	 *
	 * @param input Your text with the Highlighting-Tag
	 * @return HTML containing converted Tags
	 */
	public static String parseTags(String input) {
		if (input == null) {
			input = "";
		}

		HashMap<String, String> tagMap = new HashMap<String, String>();
		tagMap.put("highlight", "mark");

		for (HashMap.Entry<String, String> entry : tagMap.entrySet()) {
			input = input.replaceAll("\\[(/)*?(" + entry.getKey() + ")\\]", "<$1" + entry.getValue() + ">");
		}

		return input;
	}

}
