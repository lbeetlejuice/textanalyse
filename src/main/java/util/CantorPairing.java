package util;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Cantor Pairing Function, Details: https://en.wikipedia.org/wiki/Pairing_function
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160523
 */
public class CantorPairing {

	/**
	 * Applies the cantor pairing function to two integer values
	 * 
	 * @param value1
	 * @param value2
	 * @return Result from the calculation
	 */
	public static int cantorize(int value1, int value2) {
		return (value1 + value2) * (value1 + value2 + 1) / 2 + value2;
	}

	/**
	 * Inverse of the cantor pairing function
	 * 
	 * @param cantorValue
	 * @return value1
	 */
	public static int cantor1(int cantorValue) {
		int temp = (int) Math.floor(Math.sqrt(0.25 + 2 * cantorValue) - 0.5);
		return temp - cantor2(cantorValue);
	}

	/**
	 * Inverse of the cantor pairing function
	 * 
	 * @param cantorValue
	 * @return value2
	 */
	public static int cantor2(int cantorValue) {
		int temp = (int) Math.floor(Math.sqrt(0.25 + 2 * cantorValue) - 0.5);
		return cantorValue - temp * (temp + 1) / 2;
	}

}
