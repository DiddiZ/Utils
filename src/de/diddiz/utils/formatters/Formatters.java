package de.diddiz.utils.formatters;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Iterator;

public class Formatters
{
	/**
	 * Transforms all values to a {@code String} and returns the as array.
	 */
	public static <T> String[] format(Collection<T> col, Formatter<T> formatter) {
		final String[] entries = new String[col.size()];
		int idx = 0;
		for (final Iterator<T> itr = col.iterator(); itr.hasNext();)
			entries[idx++] = formatter.format(itr.next());
		return entries;
	}

	/**
	 * Divides each {@code Integer} by {@code max} and returns it in percent format.
	 */
	public static Formatter<Integer> normalizingPercentFormatter(final float max) {
		return new Formatter<Integer>() {
			private final MessageFormat format = new MessageFormat("{0,number,0.0%}");

			@Override
			public String format(Integer integer) {
				return format.format(new Object[]{integer / max});
			}
		};
	}

	/**
	 * A formatter that simply returns toString().
	 */
	@SuppressWarnings("unchecked")
	public static <T> Formatter<T> toStringFormatter() {
		return (Formatter<T>)ToStringFormatter.INSTANCE;
	}

	private static class ToStringFormatter implements Formatter<Object>
	{
		private static final Formatter<Object> INSTANCE = new ToStringFormatter();

		@Override
		public String format(Object obj) {
			return obj.toString();
		}
	}
}
