package util;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Levenshtein Distance Calculator
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class Levenshtein {
	private static final int SWITCH_COSTE = 1;

	public static int damerauLevenshteinDistance(String word, String existingWord) {
		if (word != null && word.equals(existingWord)) {
			return 0;
		}

		int[][] levenshteinMatrix = new int[word.length() + 1][existingWord.length() + 1];
		initializeMatrix(levenshteinMatrix);
		calculateMatrix(levenshteinMatrix, word, existingWord);

		return levenshteinMatrix[word.length()][existingWord.length()];
	}

	private static void initializeMatrix(int[][] levenshteinMatrix) {
		for (int i = 0; i < levenshteinMatrix.length; i++) {
			levenshteinMatrix[i][0] = i;
		}

		for (int i = 0; i < levenshteinMatrix[0].length; i++) {
			levenshteinMatrix[0][i] = i;
		}
	}

	private static int min(int[] values) {
		int result = Integer.MAX_VALUE;

		for (int value : values) {
			result = Math.min(result, value);
		}

		return result;
	}

	private static void calculateMatrix(int[][] matrix, String word, String existingWord) {
		int wordLength = word.length();
		int existingWordLength = existingWord.length();
		char[] wordChars = word.toCharArray();
		char[] existingWordChars = existingWord.toCharArray();
		char wordChar;
		char existingWordChar;
		char lastWordChar = 0;
		char lastExistingWordChar = 0;

		for (int i = 1; i <= wordLength; i++) {
			for (int j = 1; j <= existingWordLength; j++) {
				int[] values = new int[4];
				wordChar = wordChars[i - 1];
				existingWordChar = existingWordChars[j - 1];
				values[0] = matrix[i - 1][j - 1];

				if (i >= 2 && j >= 2) {
					lastWordChar = wordChars[i - 2];
					lastExistingWordChar = existingWordChars[j - 2];
				}

				if (wordChar != existingWordChar) {
					values[0]++;
				}

				values[1] = matrix[i][j - 1] + 1;
				values[2] = matrix[i - 1][j] + 1;
				values[3] = Integer.MAX_VALUE;

				if (i >= 2 && j >= 2) {
					if (wordChar == lastExistingWordChar && existingWordChar == lastWordChar) {
						values[3] = matrix[i - 2][j - 2] + SWITCH_COSTE;
					}
				}

				matrix[i][j] = min(values);
			}
		}
	}

}
