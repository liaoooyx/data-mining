package cn.liao;

import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;
import cn.liao.filter.EmojiFilter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-11 21:01.
 */
public class Executor {

	public static void main(String[] args) {
		Connection conn = DbOperator.getConnection();
		List<Cw1Dataset> list = new ArrayList<>();

		// read all records from trainingset table
		String sql = "SELECT * FROM trainingset";
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

		// filter
		list.stream().forEach(s -> s.setTweet(EmojiFilter.removeAllEmoji(s.getTweet())));

		// write all records into trainingset_no_emoji table.
		String sql2 = "INSERT INTO trainingset_no_emoji(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			conn = DbOperator.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql2); //预编译SQL，减少sql执行
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

		DbOperator.close(conn);
	}
}
