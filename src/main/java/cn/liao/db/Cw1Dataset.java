package cn.liao.db;

import java.util.function.UnaryOperator;

/**
 * Created by Yuxiang Liao on 2020-02-11 16:16.
 */
public class Cw1Dataset {
	private int id;
	private String tweet;
	private String task_a;
	private String task_b;
	private String task_c;

	public Cw1Dataset(){
	}

	public Cw1Dataset(int id, String tweet, String task_a, String task_b, String task_c) {
		this.id = id;
		this.tweet = tweet;
		this.task_a = task_a;
		this.task_b = task_b;
		this.task_c = task_c;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public String getTask_a() {
		return task_a;
	}

	public void setTask_a(String task_a) {
		this.task_a = task_a;
	}

	public String getTask_b() {
		return task_b;
	}

	public void setTask_b(String task_b) {
		this.task_b = task_b;
	}

	public String getTask_c() {
		return task_c;
	}

	public void setTask_c(String task_c) {
		this.task_c = task_c;
	}

	@Override
	public String toString() {
		return "Cw1Dataset{" + "id=" + id + ", task_a='" + task_a + '\'' + ", task_b='" + task_b + '\'' + ", task_c='" +
				task_c + '\'' + ", tweet='" + tweet + '\'' + '}';
	}

	public void filterTweet(UnaryOperator<String> filter) {
		tweet = filter.apply(tweet);
	}
}
