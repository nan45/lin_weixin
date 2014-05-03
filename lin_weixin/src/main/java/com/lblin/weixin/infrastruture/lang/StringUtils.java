package com.lblin.weixin.infrastruture.lang;

public abstract class StringUtils {

	public static boolean isEmpty(String text) {
		if (text == null) {
			return true;
		}

		return (text.length() <= 0);
	}

	public static boolean isBlank(String text) {
		if (isEmpty(text)) {
			return true;
		}

		int length = text.length();
		for (int i = 0; i < length; ++i) {
			if (!(Character.isWhitespace(text.charAt(i)))) {
				return false;
			}
		}

		return true;
	}

	public boolean startsWith(String text, String prefix) {
		if (isBlank(text)) {
			return false;
		}

		return text.startsWith(prefix);
	}

	public boolean endsWith(String text, String suffix) {
		if (isBlank(text)) {
			return false;
		}

		return text.endsWith(suffix);
	}

	public static String[] split(String text, String delimiter) {
		if (isEmpty(text)) {
			return new String[0];
		}

		return text.split(delimiter);
	}
}
