package cn.liao.db;

/**
 * Created by Yuxiang Liao on 2020-02-11 15:36.
 */

import java.io.File;
import java.io.PrintStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbOperator {

	public static final String URL = "jdbc:mysql://localhost:3306/data_mining";
	public static final String USER = "root";
	public static final String PASSWORD = "liaoyuxiang";
	public static Connection conn = null;

	static {
		try {
			//1.加载驱动程序
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2. 获得数据库连接
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return conn;
	}

	//关闭数据库的方法
	public static void close(ResultSet rs, Statement sta, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement sta, Connection conn) {
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void insertListInto(List<Cw1Dataset> list, String tableName) throws SQLException {
		//获取连接
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		//sql
		String sql = "INSERT INTO " + tableName + "(id, tweet, task_a, task_b, task_c) VALUES(?,?,?,?,?)";
		//预编译
		PreparedStatement ps = conn.prepareStatement(sql); //预编译SQL，减少sql执行
		for (Cw1Dataset dataset : list) {
			//传参
			ps.setInt(1, dataset.getId());
			ps.setString(2, dataset.getTweet());
			ps.setString(3, dataset.getTask_a());
			ps.setString(4, dataset.getTask_b());
			ps.setString(5, dataset.getTask_c());
			ps.addBatch();

		}
		ps.executeBatch();
		conn.commit();
		close(ps, conn);
	}

	public List<Cw1Dataset> readAllFrom(String tableName) throws SQLException {
		String sql = "SELECT * FROM " + tableName;
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Cw1Dataset> list = new ArrayList<Cw1Dataset>();
		while (rs.next()) {
			list.add(new Cw1Dataset(rs.getInt("id"), rs.getString("tweet"), rs.getString("task_a"),
					rs.getString("task_b"), rs.getString("task_c")));
		}
		close(rs, stmt, conn);
		return list;
	}

}