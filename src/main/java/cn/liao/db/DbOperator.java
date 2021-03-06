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

	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(Statement sta) {
		if (sta != null) {
			try {
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}