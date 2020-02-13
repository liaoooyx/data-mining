package cn.liao;

import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;
import cn.liao.filter.Filter;
import com.uttesh.exude.exception.InvalidDataException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Yuxiang Liao on 2020-02-11 21:01.
 */
public class Executor {


	public static void main(String[] args) {
		Connection conn = DbOperator.getConnection();
		//		step1To4(conn);
		//		step5(conn);
		step89(conn);
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
			//stop words
			//			s.filterTweet(s -> {
			//				try {
			//					return Filter.removeAllStopWords(s);
			//				} catch (InvalidDataException e) {
			//					e.printStackTrace();
			//				}
			//				return s;
			//			});
			//numbers
			//			s.filterTweet(Filter::removeAllNumber);
		});

		// write all records into trainingset_no_emoji table.
		String sql2 = "INSERT INTO trainingset_1234(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);


	}

	public static void step5(Connection conn) {
		String sql = "SELECT * FROM trainingset_1234";
		List<Cw1Dataset> list = readAll(conn, sql);
		ArrayList<String> normalizationTextList = readFromFile("/Users/liao/Desktop/Text Normalizatin.txt");

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTweet(normalizationTextList.get(i).trim());
		}

		String sql2 = "INSERT INTO trainingset_1234_5(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);
	}

	public static void step89(Connection conn) {
		// read all records from trainingset table
		String sql = "SELECT * FROM trainingset_1234_5";
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
		String sql2 = "INSERT INTO trainingset_1234_5_89(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);
	}

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

	public static List<Cw1Dataset> readAll(Connection conn, String sql) {
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
