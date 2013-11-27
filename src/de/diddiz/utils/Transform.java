package de.diddiz.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.google.common.base.Function;

public final class Transform
{
	/**
	 * Creates a new array by transforming the array.
	 */
	@SuppressWarnings("unchecked")
	public static <F, T> T[] toArray(Collection<F> from, Class<T> clazz, Function<F, T> function) {
		return toArray(from, (T[])Array.newInstance(clazz, from.size()), function);
	}

	/**
	 * Fills the {@code to} array with the transformation results.
	 * 
	 * From and to must have the same length.
	 * 
	 * @return the {@code to} array.
	 */
	public static <F, T> T[] toArray(Collection<F> from, T[] to, Function<F, T> function) {
		if (from.size() != to.length)
			throw new IllegalArgumentException("From and to must have the same length");
		int idx = 0;
		for (final F f : from)
			to[idx++] = function.apply(f);
		return to;
	}

	/**
	 * Creates a new array by transforming the array.
	 */
	@SuppressWarnings("unchecked")
	public static <F, T> T[] toArray(F[] from, Class<T> clazz, Function<F, T> function) {
		return toArray(from, (T[])Array.newInstance(clazz, from.length), function);
	}

	/**
	 * Fills the {@code to} array with the transformation results.
	 * 
	 * From and to must have the same length.
	 * 
	 * @return the {@code to} array.
	 */
	public static <F, T> T[] toArray(F[] from, T[] to, Function<F, T> function) {
		if (from.length != to.length)
			throw new IllegalArgumentException("From and to must have the same length");
		for (int i = 0; i < from.length; i++)
			to[i] = function.apply(from[i]);
		return to;
	}

	/**
	 * Creates a new {@code List} by transforming the array.
	 */
	public static <F, T> List<T> toList(F[] arr, Function<F, T> function) {
		final List<T> list = new ArrayList<>(arr.length);
		for (final F f : arr)
			list.add(function.apply(f));
		return list;
	}
}
