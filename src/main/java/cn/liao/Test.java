package cn.liao;

import cn.liao.db.Cw1Dataset;
import cn.liao.db.DbOperator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-15 16:58.
 */
public class Test {
	public static void main(String[] args) {
		Connection connection = DbOperator.getConnection();
		String sql = "SELECT * FROM " + "testset_1234_5";
		List<Cw1Dataset> list = TestsetExecutor.readAll(connection, sql);
		for (Cw1Dataset data : list) {
			System.out.println(data);
		}
	}
}
