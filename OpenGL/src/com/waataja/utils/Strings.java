package com.waataja.utils;

public class Strings {
	public static String combine(String[] words) {
		String str = "";
		for (String word : words) {
			str = str + word;
		}
		return str;
	}
	public static String append(String str, String suf) {
		return str + suf;
	}
	public static String after(String str, String start) {
		return str.substring(start.length() + 1);
	}
	public static float toFloat(String string) {
		return Float.valueOf(string);
	}
	public static int toInt(String string) {
		return Integer.valueOf(string);
	}
}
