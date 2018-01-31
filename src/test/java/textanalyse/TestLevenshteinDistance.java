package textanalyse;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import util.Levenshtein;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Test Levenshtein Distance
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class TestLevenshteinDistance {

	@Test
	public void testDistance0() {
		assertEquals(0, Levenshtein.damerauLevenshteinDistance("test", "test"));
	}

	@Test
	public void testDistance1() {
		assertEquals(1, Levenshtein.damerauLevenshteinDistance("test", "tset"));
		assertEquals(1, Levenshtein.damerauLevenshteinDistance("test", "tst"));
		assertEquals(1, Levenshtein.damerauLevenshteinDistance("test", "est"));
		assertEquals(1, Levenshtein.damerauLevenshteinDistance("test", "est"));
		assertEquals(1, Levenshtein.damerauLevenshteinDistance("test", "ttest"));
	}

	@Test
	public void testDistance2() {
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "etts"));
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "te"));
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "tt"));
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "st"));
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "es"));
		assertEquals(2, Levenshtein.damerauLevenshteinDistance("test", "ttestt"));
	}

}
