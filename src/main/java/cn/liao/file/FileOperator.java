package cn.liao.file;

import cn.liao.Executor;
import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;

import java.io.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yuxiang Liao on 2020-02-13 21:35.
 */
public class FileOperator {

	public static ArrayList<String> readFromFile(String fullPath) {
		ArrayList<String> list = new ArrayList<>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(fullPath));
			list = in.lines().collect(Collectors.toCollection(ArrayList::new));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void writeArffFile(List<Cw1Dataset> list, String fileName, String relation) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".arff"));
		out.write("@relation " + relation);
		out.write("\r\n\r\n");
		out.write("@attribute id numeric");
		out.write("\r\n");
		out.write("@attribute tweet string");
		out.write("\r\n");
		out.write("@attribute sub_a {OFF,NOT}");
		out.write("\r\n");
		out.write("@attribute sub_b {TIN,UNT,NULL}");
		out.write("\r\n");
		out.write("@attribute sub_c {IND,GRP,OTH,NULL}");
		out.write("\r\n\r\n");
		out.write("@data");
		out.write("\r\n");
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write(",");
			out.write("'" + data.getTweet().trim() + "'");
			out.write(",");
			out.write(data.getTask_a());
			out.write(",");
			out.write(data.getTask_b());
			out.write(",");
			out.write(data.getTask_c());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeArffFileForTask_a(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                   IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".arff"));
		out.write("@relation " + relation);
		out.write("\r\n\r\n");
		out.write("@attribute id numeric");
		out.write("\r\n");
		out.write("@attribute tweet string");
		out.write("\r\n");
		out.write("@attribute sub_a {OFF,NOT}");
		out.write("\r\n\r\n");
		out.write("@data");
		out.write("\r\n");
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write(",");
			out.write("'" + data.getTweet().trim() + "'");
			out.write(",");
			out.write(data.getTask_a());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeArffFileForTask_b(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                   IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".arff"));
		out.write("@relation " + relation);
		out.write("\r\n\r\n");
		out.write("@attribute id numeric");
		out.write("\r\n");
		out.write("@attribute tweet string");
		out.write("\r\n");
		out.write("@attribute sub_b {TIN,UNT,NULL}");
		out.write("\r\n\r\n");
		out.write("@data");
		out.write("\r\n");
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write(",");
			out.write("'" + data.getTweet().trim() + "'");
			out.write(",");
			out.write(data.getTask_b());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeArffFileForTask_c(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                   IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".arff"));
		out.write("@relation " + relation);
		out.write("\r\n\r\n");
		out.write("@attribute id numeric");
		out.write("\r\n");
		out.write("@attribute tweet string");
		out.write("\r\n");
		out.write("@attribute sub_c {IND,GRP,OTH,NULL}");
		out.write("\r\n\r\n");
		out.write("@data");
		out.write("\r\n");
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write(",");
			out.write("'" + data.getTweet().trim() + "'");
			out.write(",");
			out.write(data.getTask_c());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeTsvFile(List<Cw1Dataset> list, String fileName, String relation) throws IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".tsv"));
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write("\t");
			out.write(data.getTweet().trim());
			out.write("\t");
			out.write(data.getTask_a());
			out.write("\t");
			out.write(data.getTask_b());
			out.write("\t");
			out.write(data.getTask_c());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeTsvFileForTask_a(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                  IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".tsv"));
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write("\t");
			out.write(data.getTweet().trim());
			out.write("\t");
			out.write(data.getTask_a());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeTsvFileForTask_b(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                  IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".tsv"));
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write("\t");
			out.write(data.getTweet().trim());
			out.write("\t");
			out.write(data.getTask_b());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void writeTsvFileForTask_c(List<Cw1Dataset> list, String fileName, String relation) throws
	                                                                                                  IOException {

		BufferedWriter out = new BufferedWriter(new FileWriter("/Users/liao/Desktop/" + fileName + ".tsv"));
		int i = 1;
		for (Cw1Dataset data : list) {
			out.write(Integer.toString(data.getId()));
			out.write("\t");
			out.write(data.getTweet().trim());
			out.write("\t");
			out.write(data.getTask_c());
			out.write("\r\n");
			if (i % 100 == 0) {
				out.flush();
			}
			i++;
		}
		out.flush();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		Connection conn = DbOperator.getConnection();
		String sql = "SELECT * FROM testsetc";
		List<Cw1Dataset> list = Executor.readAll(conn, sql);
		String middleName = "testset";

//		writeTsvFile(list, "OLID_" + middleName + "_all", "OLID_" + middleName + "_all");
//		writeTsvFileForTask_a(list, "OLID_" + middleName + "_a", "OLID_" + middleName + "_a");
//		writeTsvFileForTask_b(list, "OLID_" + middleName + "_b", "OLID_" + middleName + "_b");
		writeTsvFileForTask_c(list, "OLID_" + middleName + "_c", "OLID_" + middleName + "_c");
	}
}
