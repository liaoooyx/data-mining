package cn.liao.filter;

import cn.liao.db.Cw1Dataset;
import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;

/**
 * Created by Yuxiang Liao on 2020-02-11 14:37.
 */
public class Filter {

	public String stopwords = "a,able,about,across,after,all,almost,also,am,among,an,and,any,are,as,at,be," +
			"because,been,but,by,can,cannot,could,dear,did,do,does,either,else,ever," +
			"every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in," +
			"into,is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no," +
			"nor,not,of,off,often,on,only,or,other,our,own,rather,said,say,says,she,should," +
			"since,so,some,than,that,the,their,them,then,there,these,they,this,tis,to,too,twas,us," +
			"wants,was,we,were,what,when,where,which,while,who,whom,why,will,with,would,yet,you,your";

	public static String removeAllEmoji(String target) {
		return EmojiParser.removeAllEmojis(target);
	}

	public static String removeAllSpecificString(String target) {
		// remove all @USER and URL
		return target.replaceAll("@USER ", "").replaceAll("URL|URL ", "").trim();
	}

	public static String removeAllPunctuation(String target) {
		//keep #topic, and remove all punctuation
		return target.replaceAll("(?!#)[\\pP]", "");
	}

	public static String convertToLowerCase(String target) {
		return target.toLowerCase();
	}

	public static String removeAllNumber(String target){
		return target.replaceAll("\\d","");
	}

	public static  String  removeAllStopWords(String target) throws InvalidDataException {
		return ExudeData.getInstance().filterStoppingsKeepDuplicates(target);
	}

	public static void main(String[] args) throws InvalidDataException {
		Cw1Dataset dataset = new Cw1Dataset();
		dataset.setTweet(
				"@USER @USER Go home you be drunk!!! @USER #MAGA #Trump2020 \uD83D\uDC4A\uD83C\uDDFA\uD83C\uDDF8\uD83D\uDC4A URL");
//		dataset.filterTweet(Filter::removeAllEmoji);
//		dataset.filterTweet(Filter::removeAllSpecificString);
//		dataset.filterTweet(Filter::removeAllPunctuation);
		dataset.filterTweet(s -> {
			try {
				return removeAllStopWords(s);
			} catch (InvalidDataException e) {
				e.printStackTrace();
			}
			return s;
		});
		System.out.println(dataset.getTweet());
		String inputData = "testing testing testing the keep keep the the duplicate data data in result";
		String output = ExudeData.getInstance().filterStoppingsKeepDuplicates(inputData);
		System.out.println("output : "+output);
	}
}
