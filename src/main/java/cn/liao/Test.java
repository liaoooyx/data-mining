package cn.liao;

/**
 * Created by Yuxiang Liao on 2020-02-15 16:58.
 */
public class Test {
	public static void main(String[] args) {
		String string = "asldj%als";
		System.out.println(string.replaceAll("%","\\\\%"));
	}
}
