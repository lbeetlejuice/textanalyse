package textanalyse;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;

import model.MetadataDocument;

/**
 * Algorithmen und Datenstrukturen FS 2016,
 * Praktikum 11 - Textanalyse,
 * Test Metadata Document
 * 
 * @author Lukas Buchter, Frank Holzach, Savin Niederer
 * @version 20160524
 */
public class TestMetadataDocument {
	private MetadataDocument document = new MetadataDocument(Paths.get("C:/Temp/reviewsbymembers50000/A10AECKZ0GRKG/can be bought at flylady net.txt"), 0);

	@Test
	public void testGetAuthor() {
		assertEquals("Check author", "A10AECKZ0GRKG", document.getAuthor());
	}

	@Test
	public void testGetFileName() {
		assertEquals("Check filename", "can be bought at flylady net", document.getFileName());
	}

}
