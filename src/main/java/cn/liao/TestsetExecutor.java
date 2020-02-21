package cn.liao;

import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;
import cn.liao.file.FileOperator;
import cn.liao.filter.Filter;
import com.uttesh.exude.exception.InvalidDataException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-20 16:15.
 */
public class TestsetExecutor {
	public static void main(String[] args) {
		Connection conn = DbOperator.getConnection();
//		step1To4(conn,"testset","testset_1234");
//		step5or6or7(conn,"/Users/liao/Desktop/Testset Both","testset_1234","testset_1234_7");
		step89(conn,"testset_1234_5","testset_1234_5_89");
		step89(conn,"testset_1234_6","testset_1234_6_89");
		step89(conn,"testset_1234_7","testset_1234_7_89");
		DbOperator.close(conn);
	}


	public static void step1To4(Connection conn,String fromTable, String toTable) {

		// read all records from trainingset table
		String sql = "SELECT * FROM " + fromTable;
		List<Cw1Dataset> list = readAll(conn, sql);

		// filter, cautious about the order
		list.stream().forEach(s -> {
			s.filterTweet(Filter::removeAllEmoji);
			s.filterTweet(Filter::removeAllSpecificString);
			s.filterTweet(Filter::removeAllPunctuation);
			s.filterTweet(Filter::convertToLowerCase);
		});

		// write all records into trainingset_no_emoji table.
		String sql2 = "INSERT INTO " + toTable + "(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		writeAll(conn, sql2, list);

	}

	public static void step5or6or7(Connection conn, String filePath, String fromTable, String toTable) {
		String sql = "SELECT * FROM " + fromTable;
		List<Cw1Dataset> list = readAll(conn, sql);
		ArrayList<String> inputList = FileOperator.readFromFile(filePath);

		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTweet(inputList.get(i).trim().toLowerCase());
		}

		String sql2 = "INSERT INTO " + toTable + "(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
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

	public static List<Cw1Dataset> readAll(Connection conn, String sql) {
		List<Cw1Dataset> list = new ArrayList<>();
		Statement stm = null;
		ResultSet rs = null;
		try {
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
