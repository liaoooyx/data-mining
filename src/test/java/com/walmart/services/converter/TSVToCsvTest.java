package com.walmart.services.converter;

import java.util.Arrays;

import org.junit.Test;

import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

/**
 * Unit test for simple App.
 */
public class TSVToCsvTest {
	//String input = "1\t0\t3029\t0\tNULL\t0\t3029\t1\t0\t\tPRODUCE\tProduce\t\tProduce\t\t\t\t1\tea\t3\t2\t4\tNULL\tMandarin\t\"Mandarin\tSatusuma\"\t\t\t\t\t\t\t1/2/15 9:35\t\t1\tmedium mandarin\tNULL";

	//String input = "4394\t1\t0\tPRODUCE\tProduce\t1\tea\t9\t4\t54\tNULL\tPapaya\tPapaya\t1\t\"fruit\tNULL";
	String input = "518600143\t518600143\t0\t0\tnull\tSOAP\tRainbow\tnull\tRainbow Research\t170 Wilbur Place Bohemia	 NY 11716\t800-722-9595\tNULL\t2\tNULL\t4\t1\t1\t2\t2fl oz (60 ml)\tHand Sanitizer\tHand Sanitizer	 for Kids\tOrganic herbal. Aloe. Chamomile. Lemon grass. Marigold. Rainbow Hand Sanitizer For Kids gently protects against bacteria and germs. Made with organic herbal extracts. Non drying.\tnull\tApply a few drops to hands and massage until dry.\t\"Active Ingredient: SD Alcohol (60%). Purified Water	 Vegetable Glycerin	 Organic Herbal Extracts of Marigold\"";
			
	@Test
	public void runTsvTest() {
		CsvFormat format = new CsvFormat();
	    format.setDelimiter('\t');
	    format.setLineSeparator("\n");
	    
	    CsvParserSettings settings = new CsvParserSettings();
	    settings.setDelimiterDetectionEnabled(true);
	    settings.setMaxCharsPerColumn(-1);
	    settings.setMaxColumns(512);
	    settings.setFormat(format);
		
		String[] csvRow = new CsvParser(settings).parseLine(input);

		System.out.println("csvRow: " + Arrays.toString(csvRow));
		for (String value : csvRow) {
			System.out.println(value);
		}
	}
}
