package cn.liao;

import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;
import cn.liao.file.FileOperator;
import cn.liao.filter.Filter;
import com.uttesh.exude.exception.InvalidDataException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-11 21:01.
 */
public class TrainsetExecutor {


	public static void main(String[] args) throws IOException {
		Connection conn = DbOperator.getConnection();
				step1To4(conn);
		//		step5or6or7(conn, "/Users/liao/Desktop/Text Normalizatin", "trainingset_1234_5");
		//		step89(conn, "trainingset_1234_5", "trainingset_1234_5_89");
		//		step5or6or7(conn, "/Users/liao/Desktop/Disambiguated concepts", "trainingset_1234_6");
		//		step89(conn, "trainingset_1234_6", "trainingset_1234_6_89");
		//		step5or6or7(conn, "/Users/liao/Desktop/Both", "trainingset_1234_7");
		//		step89(conn, "trainingset_1234_7", "trainingset_1234_7_89");

		List<Cw1Dataset> list5 = TrainsetExecutor.readAll(conn, "trainingset_1234_5_89");
//		FileOperator.writeArffFile(list5, "OLID_" + "trainingset_normal" + "_all", "OLID_" + "trainingset_Text_Normalization" + "_all");
//		FileOperator.writeArffFileForTask_a(list5, "OLID_" + "trainingset_normal" + "_a", "OLID_" + "trainingset_Text_Normalization" + "_a");
//		FileOperator.writeArffFileForTask_b(list5, "OLID_" + "trainingset_normal" + "_b", "OLID_" + "trainingset_Text_Normalization" + "_b");
//		FileOperator.writeArffFileForTask_c(list5, "OLID_" + "trainingset_normal" + "_c", "OLID_" + "trainingset_Text_Normalization" + "_c");
//		FileOperator.writeTsvFile(list5, "OLID_" + "trainingset_disamb" + "_all", "OLID_" + "trainingset_Text_Normalizationn" + "_all");
//		FileOperator.writeTsvFileForTask_a(list5, "OLID_" + "trainingset_normal" + "_a", "OLID_" + "trainingset_Text_Normalization" + "_a");
//		FileOperator.writeTsvFileForTask_b(list5, "OLID_" + "trainingset_normal" + "_b", "OLID_" + "trainingset_Text_Normalization" + "_b");
//		FileOperator.writeTsvFileForTask_c(list5, "OLID_" + "trainingset_normal" + "_c", "OLID_" + "trainingset_Text_Normalization" + "_c");


		//		List<Cw1Dataset> list6 = Executor.readAll(conn, "trainingset_1234_6_89");
//		FileOperator.writeArffFile(list6, "OLID_" + "trainingset_disamb" + "_all", "OLID_" + "trainingset_Disambiguation" + "_all");
//		FileOperator.writeArffFileForTask_a(list6, "OLID_" + "trainingset_disamb" + "_a", "OLID_" + "trainingset_Disambiguation" + "_a");
//		FileOperator.writeArffFileForTask_b(list6, "OLID_" + "trainingset_disamb" + "_b", "OLID_" + "trainingset_Disambiguation" + "_b");
//		FileOperator.writeArffFileForTask_c(list6, "OLID_" + "trainingset_disamb" + "_c", "OLID_" + "trainingset_Disambiguation" + "_c");
//		FileOperator.writeTsvFile(list6, "OLID_" + "trainingset_disamb" + "_all", "OLID_" + "trainingset_Disambiguation" + "_all");
//		FileOperator.writeTsvFileForTask_a(list6, "OLID_" + "trainingset_disamb" + "_a", "OLID_" + "trainingset_Disambiguation" + "_a");
//		FileOperator.writeTsvFileForTask_b(list6, "OLID_" + "trainingset_disamb" + "_b", "OLID_" + "trainingset_Disambiguation" + "_b");
//		FileOperator.writeTsvFileForTask_c(list6, "OLID_" + "trainingset_disamb" + "_c", "OLID_" + "trainingset_Disambiguation" + "_c");

//		List<Cw1Dataset> list7 = Executor.readAll(conn, "trainingset_1234_7_89");
//		FileOperator.writeArffFile(list7, "OLID_" + "trainingset_both" + "_all", "OLID_" + "trainingset_Lingo_translation_and_Disambiguation" + "_all");
//		FileOperator.writeArffFileForTask_a(list7, "OLID_" + "trainingset_both" + "_a", "OLID_" + "trainingset_Lingo_translation_and_Disambiguation" + "_a");
//		FileOperator.writeArffFileForTask_b(list7, "OLID_" + "trainingset_both" + "_b", "OLID_" + "trainingset_Lingo_translation_and_Disambiguation" + "_b");
//		FileOperator.writeArffFileForTask_c(list7, "OLID_" + "trainingset_both" + "_c", "OLID_" + "trainingset_Lingo_translation_and_Disambiguation" + "_c");
//		FileOperator.writeTsvFile(list7, "OLID_" + "trainingset_both" + "_all", "OLID_" + "trainingset_Lingo translation and Disambiguation" + "_all");
//		FileOperator.writeTsvFileForTask_a(list7, "OLID_" + "trainingset_both" + "_a", "OLID_" + "trainingset_Lingo translation and Disambiguation" + "_a");
//		FileOperator.writeTsvFileForTask_b(list7, "OLID_" + "trainingset_both" + "_b", "OLID_" + "trainingset_Lingo translation and Disambiguation" + "_b");
//		FileOperator.writeTsvFileForTask_c(list7, "OLID_" + "trainingset_both" + "_c", "OLID_" + "trainingset_Lingo translation and Disambiguation" + "_c");

		DbOperator.close(conn);

	}

	public static void step1To4(Connection conn) {

		// read all records from trainingset table
		String sql = "SELECT * FROM trainingset";
		List<Cw1Dataset> list = readAll(conn, sql);

		// filter, cautious about the order
		list.stream().forEach(s -> {
			s.filterTweet(Filter::removeAllEmoji);
			s.filterTweet(Filter::removeAllSpecificString);
			s.filterTweet(Filter::removeAllPunctuation);
			s.filterTweet(Filter::convertToLowerCase);
		});

		// write all records into trainingset_no_emoji table.
		String sql2 = "INSERT INTO trainingset_1234(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);

	}

	public static void step5or6or7(Connection conn, String filePath, String tableName) {
		String sql = "SELECT * FROM trainingset_1234";
		List<Cw1Dataset> list = readAll(conn, sql);
		ArrayList<String> inputList = FileOperator.readFromFile(filePath);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTweet(inputList.get(i).trim().toLowerCase());
		}

		String sql2 = "INSERT INTO " + tableName + "(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);
	}

	public static void step89(Connection conn, String fromTable, String toTable) {
		// read all records from trainingset table
		String sql = "SELECT * FROM " + fromTable;
		List<Cw1Dataset> list = readAll(conn, sql);

		// filter, cautious about the order
		list.stream().forEach(s -> {
			// stop words
			s.filterTweet(k -> {
				try {
					return Filter.removeAllStopWords(k);
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
				return k;
			});
			// numbers
			s.filterTweet(Filter::removeAllNumber);
		});

		// write all records into trainingset_no_emoji table.
		String sql2 = "INSERT INTO " + toTable + "(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);
	}

	public static List<Cw1Dataset> readAll(Connection conn, String tableName) {
		String sql = "SELECT * FROM " + tableName;
		List<Cw1Dataset> list = new ArrayList<>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DbOperator.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				list.add(new Cw1Dataset(rs.getInt("id"), rs.getString("tweet"), rs.getString("task_a"),
						rs.getString("task_b"), rs.getString("task_c")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbOperator.close(rs);
			DbOperator.close(stm);
		}
		return list;
	}

	public static void writeAll(Connection conn, String sql, List<Cw1Dataset> list) {
		PreparedStatement ps = null;
		try {
			conn = DbOperator.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql); //预编译SQL，减少sql执行
			for (Cw1Dataset dataset : list) {
				ps.setInt(1, dataset.getId());
				ps.setString(2, dataset.getTweet());
				ps.setString(3, dataset.getTask_a());
				ps.setString(4, dataset.getTask_b());
				ps.setString(5, dataset.getTask_c());
				ps.addBatch();
			}
			ps.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbOperator.close(ps);
		}
	}


}
