package com.walmart.services.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.univocity.parsers.common.TextWritingException;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class TsvToCsvConverter {
	private static String encoding = "UTF-8";
	
	public static void main(String[] args) throws Exception {
		if(args.length < 3) {
	        System.out.println("Proper Usage is: <filePath> <inputTsvFile> <outputCsvFile>");
	        System.exit(0);
	    }
		String filePath = args[0];
		File inputFile = new File(args[1]);
		File outputFile = new File(args[2]);
		
		System.out.println("************** TSV To CSV Converter **************");
		System.out.println("File Path: " + filePath);
		System.out.println("Input TSV file: " + inputFile.getAbsolutePath());
		System.out.println("Output TSV file: " + outputFile.getAbsolutePath() + "\n\n");
		
		TsvToCsvConverter converter = new TsvToCsvConverter();
	    File folder = new File(filePath);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith("tsv")) {
            	converter.readAndWrite(inputFile, outputFile, encoding);
            }
        }
	}
	
	private void readAndWrite(File inputFile, File outputFile, String encoding) {
		TsvParserSettings settings = new TsvParserSettings();
		settings.getFormat().setLineSeparator("\n");
		TsvParser parser = new TsvParser(settings);
		
		CsvWriter writer = createCsvWriter(outputFile, encoding);

		//call beginParsing to read records one by one, iterator-style.
		int index = 0;
		try {
			parser.beginParsing(new InputStreamReader(new FileInputStream(inputFile), encoding));
			String[] row;
			System.out.println("Reading TSV file: " + inputFile);
			while ((row = parser.parseNext()) != null) {
				System.out.println("Reading row [" + index + "]: " + Arrays.toString(row));
				convertToCsv(row, writer, index);
				index++;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			parser.stopParsing();
			writer.close();
		}
		System.out.println("\n\n************** TSV to CSV converted succesfully **************");
	}
	
	private CsvWriter createCsvWriter(File outputFile, String encoding) {
	    CsvWriterSettings settings = new CsvWriterSettings();
	    settings.setQuoteAllFields(true);

	    try {
	        return new CsvWriter(new OutputStreamWriter(new FileOutputStream(outputFile), encoding), settings);
	    } catch (IOException e) {
	        throw new IllegalArgumentException("Error writing to " + outputFile.getAbsolutePath(), e);
	    }
	}
	
	private void convertToCsv(String[] row, CsvWriter writer, int index) {
		try {
			System.out.println("Writing row [" + index + "]: " + Arrays.toString(row));
			writer.writeRow(row);
		} catch (Exception e) {
			 throw new TextWritingException("Error writing row: " + row + " at line no:" + index);
		}
	}
}
