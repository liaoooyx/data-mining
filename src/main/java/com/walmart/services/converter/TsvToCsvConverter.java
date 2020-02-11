package com.walmart.services.converter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.univocity.parsers.common.TextWritingException;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

public class TsvToCsvConverter {
	private static String encoding = "UTF-8";
	private static String lineSeparator = "\n";
	private static char delimiter = '\t';
	
	public static void main(String[] args) throws Exception {
		if(args.length < 2) {
	        System.out.println("Proper Usage is: <inputTsvFile> <outputCsvFile>");
	        System.exit(0);
	    }
		File inputFile = new File(args[0]);
		File outputFile = new File(args[1]);
		
		String currentDirectory = System.getProperty("user.dir");
		String logFile = currentDirectory + File.separator + "tsvtocsv.log";
		PrintStream out = new PrintStream(new File(logFile));
		System.setOut(out);
		
		System.out.println("************** TSV To CSV Converter **************");
		System.out.println("Input TSV file: " + inputFile.getAbsolutePath());
		System.out.println("Output TSV file: " + outputFile.getAbsolutePath() + "\n\n");
		
		TsvToCsvConverter converter = new TsvToCsvConverter();
        if (inputFile.exists() && inputFile.getName().endsWith("tsv")) {
        	converter.readAndWrite(inputFile, outputFile, encoding);
        } else {
        	System.out.println("Please pass a valid TSV file");
        }
	}
	
	private void readAndWrite(File inputFile, File outputFile, String encoding) throws IOException {
		CsvFormat format = new CsvFormat();
	    format.setDelimiter(delimiter);
	    format.setLineSeparator(lineSeparator);
	    
	    CsvParserSettings settings = new CsvParserSettings();
	    settings.setDelimiterDetectionEnabled(true);
	    settings.setMaxCharsPerColumn(-1);
	    settings.setMaxColumns(512);
	    settings.setFormat(format);
	    
	    CsvParser parser = new CsvParser(settings);
		CsvWriter writer = createCsvWriter(outputFile, encoding);
		
		//call beginParsing to read records one by one, iterator-style.
		int index = 1;
		int headerLength = 0;
		try {
			parser.beginParsing(getReader(inputFile, encoding));
			String[] row = null;
			System.out.println("Reading TSV file: " + inputFile);
			try {
				while ((row = parser.parseNext()) != null) {
					if (index == 1) {
						headerLength = row.length;
					}
					System.out.println("Header length: " + headerLength);
					System.out.println("Row length: " + row.length);
					if (row.length > headerLength) {
						System.out.println("Skipping row [" + index + "]: " + Arrays.toString(row));
						index++;
						continue; // skip the row if the number of columns
									// doesn't match number of headers
					}
					convertToCsv(row, writer, index);
					index++;
				}
			} catch (Exception e) {
				System.out.println("Error processing row: " + Arrays.toString(row));
			}
		} finally {
			parser.stopParsing();
			writer.close();
		}
		System.out.println("\nTotal number of rows converted from CSV to TSV after skipping invalid rows: " + writer.getRecordCount());
		System.out.println("\n\n************** TSV to CSV converted successfully **************");
	}
	
	public Reader getReader(File inputFile, String encoding) {
		try {
			return new InputStreamReader(new FileInputStream(inputFile), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Unable to read inputFile", e);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private CsvWriter createCsvWriter(File outputFile, String encoding) {
	    CsvWriterSettings settings = new CsvWriterSettings();
	    settings.getFormat().setLineSeparator(lineSeparator);
	    settings.setHeaderWritingEnabled(true);
	    settings.setIgnoreLeadingWhitespaces(false);
		settings.setIgnoreTrailingWhitespaces(false);
		settings.setMaxCharsPerColumn(-1);

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
