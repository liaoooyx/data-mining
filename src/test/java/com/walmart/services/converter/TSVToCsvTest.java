package com.walmart.services.converter;

import java.util.Arrays;

import org.junit.Test;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

/**
 * Unit test for simple App.
 */
public class TSVToCsvTest {
	String input = "1\t0\t3029\t0\tNULL\t0\t3029\t1\t0\t\tPRODUCE\tProduce\t\tProduce\t\t\t\t1\tea\t3\t2\t4\tNULL\tMandarin\t\"Mandarin\tSatusuma\"\t\t\t\t\t\t\t1/2/15 9:35\t\t1\tmedium mandarin\tNULL";

	@Test
	public void runTsvTest() {

		String[] tsvRow = new TsvParser(new TsvParserSettings()).parseLine(input);

		CsvParserSettings settings = new CsvParserSettings();
		settings.getFormat().setDelimiter('\t');
		String[] csvRow = new CsvParser(settings).parseLine(input);

		System.out.println("tsvRow: " + Arrays.toString(tsvRow));
		for (String value : tsvRow) {
			System.out.println(value);
		}
		System.out.println(Arrays.toString(csvRow));
		for (String value : csvRow) {
			System.out.println(value);
		}
	}
}
