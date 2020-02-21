package cn.liao.file;

/**
 * Created by Yuxiang Liao on 2020-02-15 15:48.
 */
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.experiment.DatabaseUtils;
import weka.experiment.InstanceQuery;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */

public class ArffFileOperator {

	public static final String USER = "root";
	public static final String PASSWORD = "liaoyuxiang";

	private void saveToArffFile(Instances dataSet, File file) throws IOException {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		saver.setFile(file);
		saver.writeBatch();
	}

	public static void main(String[] args) throws Exception {

		InstanceQuery query = new InstanceQuery();
		query.setUsername(USER);
		query.setPassword(PASSWORD);
		query.setQuery("select id,tweet,task_a from testsetb");
		// You can declare that your data set is sparse
		 query.setSparseData(true);
		Instances data = query.retrieveInstances();
		System.out.println(data);
	}
}
