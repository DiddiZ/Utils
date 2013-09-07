package de.diddiz.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import de.diddiz.utils.factories.Factory;

public final class ArrayUtils
{
	public static String[] arrayOf(Collection<String> col) {
		return col != null ? col.toArray(new String[col.size()]) : new String[0];
	}

	@SafeVarargs
	public static <T> void concat(T[] bigArray, T[]... smallArrays) {
		int offset = 0;
		for (final T[] cur : smallArrays) {
			System.arraycopy(cur, 0, bigArray, offset, cur.length);
			offset += cur.length;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] createArray(int len, Factory<T> factory) {
		final T t = factory.create();
		final T[] arr = (T[])Array.newInstance(t.getClass(), len);
		arr[0] = t;
		fill(arr, 1, len - 1, factory);
		return arr;
	}

	public static char[] createCharArray(int len, char c) {
		final char[] arr = new char[len];
		Arrays.fill(arr, c);
		return arr;
	}

	public static <T> void fill(T[] arr, Factory<T> factory) {
		fill(arr, 0, arr.length, factory);
	}

	public static <T> void fill(T[] arr, int off, int len, Factory<T> factory) {
		for (int i = off; i < off + len; i++)
			arr[i] = factory.create();
	}
}
