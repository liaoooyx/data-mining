package cn.liao.filter;

import com.vdurmont.emoji.EmojiParser;

import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-11 14:37.
 */
public class EmojiFilter {

	public static String removeAllEmoji(String string){
		return EmojiParser.removeAllEmojis(string);
	}

	public static void main(String[] args) {
		String text = "@USER @USER Go home you’re drunk!!! @USER #MAGA #Trump2020 \uD83D\uDC4A\uD83C\uDDFA\uD83C\uDDF8\uD83D\uDC4A URL";
		text = EmojiParser.removeAllEmojis(text);
		System.out.println(text);
	}
}
