package com.lblin.weixin.infrastruture.lang;

import java.util.Collection;
import java.util.Map;

public abstract class Preconditions {

	public static void isTrue(boolean expression) {
		isTrue(expression, "[Assertion failed] - this expression must be true");
	}

	public static void isTrue(boolean expression, String message) {
		isTrue(expression, new IllegalArgumentException(message));
	}

	public static void isTrue(boolean expression,
			RuntimeException throwIfAssertFail) {
		if (!(expression))
			throw throwIfAssertFail;
	}

	public static void isNull(Object object) {
		isNull(object, "[Assertion failed] - the object argument must be null");
	}

	public static void isNull(Object object, String message) {
		isNull(object, new IllegalArgumentException(message));
	}

	public static void isNull(Object object, RuntimeException throwIfAssertFail) {
		if (object != null)
			throw throwIfAssertFail;
	}

	public static void notNull(Object object) {
		notNull(object,
				"[Assertion failed] - this argument is required; it must not be null");
	}

	public static void notNull(Object object, String message) {
		notNull(object, new IllegalArgumentException(message));
	}

	public static void notNull(Object object, RuntimeException throwIfAssertFail) {
		if (object == null)
			throw throwIfAssertFail;
	}

	public static void hasLength(String text) {
		hasLength(
				text,
				"[Assertion failed] - this String argument must have length; it must not be null or empty");
	}

	public static void hasLength(String text, String message) {
		hasLength(text, new IllegalArgumentException(message));
	}

	public static void hasLength(String text, RuntimeException throwIfAssertFail) {
		if (!(isStringHasLength(text)))
			throw throwIfAssertFail;
	}

	private static boolean isStringHasLength(String text) {
		return ((text != null) && (text.length() > 0));
	}

	public static void hasText(String text) {
		hasText(text,
				"[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
	}

	public static void hasText(String text, String message) {
		hasText(text, new IllegalArgumentException(message));
	}

	public static void hasText(String text, RuntimeException throwIfAssertFail) {
		if (!(isStringHasText(text)))
			throw throwIfAssertFail;
	}

	private static boolean isStringHasText(String text) {
		if (!(isStringHasLength(text))) {
			return false;
		}

		int strLen = text.length();
		for (int i = 0; i < strLen; ++i) {
			if (!(Character.isWhitespace(text.charAt(i)))) {
				return true;
			}
		}
		return false;
	}

	public static void doesNotContain(String textToSearch, String substring) {
		doesNotContain(textToSearch, substring,
				"[Assertion failed] - this String argument must not contain the substring ["
						+ substring + "]");
	}

	public static void doesNotContain(String textToSearch, String substring,
			String message) {
		doesNotContain(textToSearch, substring, new IllegalArgumentException(
				message));
	}

	public static void doesNotContain(String textToSearch, String substring,
			RuntimeException throwIfAssertFail) {
		if ((!(isStringHasLength(textToSearch)))
				|| (!(isStringHasLength(substring)))
				|| (textToSearch.indexOf(substring) == -1))
			return;
		throw throwIfAssertFail;
	}

	public static void notEmpty(Object[] array) {
		notEmpty(
				array,
				"[Assertion failed] - this array must not be empty: it must contain at least 1 element");
	}

	public static void notEmpty(Object[] array, String message) {
		notEmpty(array, new IllegalArgumentException(message));
	}

	public static void notEmpty(Object[] array,
			RuntimeException throwIfAssertFail) {
		if (isEmptyArray(array))
			throw throwIfAssertFail;
	}

	private static boolean isEmptyArray(Object[] array) {
		return ((array == null) || (array.length <= 0));
	}

	public static void noNullElements(Object[] array, String message) {
		if (array != null)
			for (Object element : array)
				if (element == null)
					throw new IllegalArgumentException(message);
	}

	public static void noNullElements(Object[] array) {
		noNullElements(array,
				"[Assertion failed] - this array must not contain any null elements");
	}

	public static void noNullElements(Object[] array,
			RuntimeException throwIfAssertFail) {
		if (array != null)
			for (Object element : array)
				if (element == null)
					throw throwIfAssertFail;
	}

	public static void notEmpty(Collection<?> collection, String message) {
		notEmpty(collection, new IllegalArgumentException(message));
	}

	public static void notEmpty(Collection<?> collection) {
		notEmpty(
				collection,
				"[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
	}

	public static void notEmpty(Collection<?> collection,
			RuntimeException throwIfAssertFail) {
		if (isEmptyCollection(collection))
			throw throwIfAssertFail;
	}

	private static boolean isEmptyCollection(Collection<?> collection) {
		return ((collection == null) || (collection.isEmpty()));
	}

	public static void notEmpty(Map<?, ?> map, String message) {
		notEmpty(map, new IllegalArgumentException(message));
	}

	public static void notEmpty(Map<?, ?> map) {
		notEmpty(
				map,
				"[Assertion failed] - this map must not be empty; it must contain at least one entry");
	}

	public static void notEmpty(Map<?, ?> map,
			RuntimeException throwIfAssertFail) {
		if (isEmptyMap(map))
			throw throwIfAssertFail;
	}

	private static boolean isEmptyMap(Map<?, ?> map) {
		return ((map == null) || (map.isEmpty()));
	}

	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!(type.isInstance(obj)))
			throw new IllegalArgumentException(message + "Object of class ["
					+ ((obj != null) ? obj.getClass().getName() : "null")
					+ "] must be an instance of " + type);
	}

	public static void isInstanceOf(Class<?> clazz, Object obj) {
		isInstanceOf(clazz, obj, "");
	}

	public static void isInstanceOf(Class<?> type, Object obj,
			RuntimeException throwIfAssertFail) {
		notNull(type, "Type to check against must not be null");
		if (!(type.isInstance(obj)))
			throw throwIfAssertFail;
	}

	public static void isAssignable(Class<?> superType, Class<?> subType,
			String message) {
		notNull(superType, "Type to check against must not be null");
		if ((subType == null) || (!(superType.isAssignableFrom(subType))))
			throw new IllegalArgumentException(message + subType
					+ " is not assignable to " + superType);
	}

	public static void isAssignable(Class<?> superType, Class<?> subType) {
		isAssignable(superType, subType, "");
	}

	public static void state(boolean expression, String message) {
		if (!(expression))
			throw new IllegalStateException(message);
	}

	public static void state(boolean expression) {
		state(expression,
				"[Assertion failed] - this state invariant must be true");
	}
}
