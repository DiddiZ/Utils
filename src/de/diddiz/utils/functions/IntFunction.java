package de.diddiz.utils.functions;

public interface IntFunction<T>
{
	/**
	 * Equivalent to {@link com.google.common.base.Function Function} for {@code int}.
	 */
	public int apply(T input);
}
