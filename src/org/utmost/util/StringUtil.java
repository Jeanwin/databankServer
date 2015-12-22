package org.utmost.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * String util class, all methods are decorated static
 * No new instance, private constructor
 * @author bull
 *
 */
public class StringUtil {

	/**
	 * private constructor, stop new StringUtil()
	 */
	private StringUtil() {}

	/**
	 * judging how many Chinese characters
	 * 
	 * @param str
	 * @return
	 */
	public static int getChineseCount(String str) {
		String temp = null;
		Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
		Matcher m = p.matcher(str);
		int count = 0;
		while (m.find()) {
			temp = m.group(0);
			count += temp.length();
			System.out.println(temp + ":" + temp.length());
		}
		return count;
	}

	/**
	 * set fill character
	 * 
	 * @param str
	 *            original char
	 * @param fill
	 *            fill character
	 * @param digit
	 *            total digit after fill
	 * @param startOrend
	 *            fill start and end of original character(1,-1)
	 * @return
	 * @throws Exception
	 */
	public static String fChar(String str, String fill, int digit,
			int startOrend) throws Exception {
		if (!(str.length() > digit)) {
			// handle Chinese problem
			// int slength=new String(str.getBytes(),"ISO-8859-1").length();
			int slength = str.length();
			int i = digit - slength;
			StringBuffer fillStr = new StringBuffer();
			for (int x = 0; x < i; x++)
				fillStr.append(fill);
			if (startOrend >= 0)
				return fillStr + str;
			else
				return str + fillStr;
		} else {
			throw new Exception("fillCharacter Exception");
		}
	}

	/**
	 * get string length
	 * 
	 * @param source
	 *            String
	 * @return string byte length
	 */
	public static int getByteLength(String source) {
		int len = 0;
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			int highByte = c >>> 8;
			len += highByte == 0 ? 1 : 2;
		}
		return len;
	}

	/**
	 * remove right spacer
	 * 
	 * @param value
	 *            string will remove right spacer
	 * @return string removed right spacer
	 */
	public static String trimRight(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int endIndex = -1;
		for (int i = ch.length - 1; i > -1; i--) {
			if (Character.isWhitespace(ch[i])) {
				endIndex = i;
			} else {
				break;
			}
		}
		if (endIndex != -1) {
			result = result.substring(0, endIndex);
		}
		return result;
	}

	/**
	 * remove left spacer
	 * 
	 * @param value
	 *            string will remove left spacer
	 * @return string removed left spacer
	 */
	public static String trimLeft(String value) {
		String result = value;
		if (result == null)
			return result;
		char ch[] = result.toCharArray();
		int index = -1;
		for (int i = 0; i < ch.length; i++) {
			if (Character.isWhitespace(ch[i])) {
				index = i;
			} else {
				break;
			}
		}
		if (index != -1) {
			result = result.substring(index + 1);
		}
		return result;
	}

	/**
	 * piece together string array to a string delimiter appointed
	 * 
	 * @param array
	 *            string array
	 * @param delim
	 *            delimiter, if is null, use "" as delimiter(non delimiter)
	 * @return string having delimiter
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * String if contain string appointed
	 * 
	 * @param strings
	 *            string array
	 * @param string
	 *            String
	 * @param caseSensitive
	 *            if upper case or lower case sensitive
	 * @return contain return true, or not return false
	 */
	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * parse string getting inputstream
	 * @param str
	 * @return
	 */
	public static InputStream parseInputStream(String str) {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		return is;
	}
	/**
	 * main test
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String input = "中文答复fd何as最的天dafds工作df高fdsdfs";
		System.out.println(StringUtil.getChineseCount(input));

		String fstr = "123哈哈";
		System.out.println(StringUtil.fChar("", "中", 10, 1));
		System.out.println(StringUtil.fChar(fstr, "中", 10, 1).length());

		System.out.println(StringUtil.getByteLength(fstr));
	}
}
