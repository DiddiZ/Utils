package de.diddiz.utils.functions;

import com.google.common.base.Function;

public final class FunctionUtils
{
	/**
	 * Returns the variable for that the function returns the highest {@code Integer}.
	 */
	public static <T> T max(Iterable<T> ts, Function<T, Integer> func) {
		T maxT = null;
		int max = Integer.MIN_VALUE;
		for (final T t : ts) {
			final Integer i = func.apply(t);
			if (i != null && i > max) {
				maxT = t;
				max = i;
			}
		}
		return maxT;
	}
}
