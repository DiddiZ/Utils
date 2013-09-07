package de.diddiz.utils;

import de.diddiz.utils.functions.IntFunction;

public final class FunctionUtils
{
	/**
	 * Returns the variable for that the function returns the largest {@code int}.
	 */
	public static <T> T max(Iterable<T> ts, IntFunction<T> func) {
		T maxT = null;
		int max = Integer.MIN_VALUE;
		for (final T t : ts) {
			final int i = func.apply(t);
			if (i > max) {
				maxT = t;
				max = i;
			}
		}
		return maxT;
	}
}
