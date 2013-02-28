package de.diddiz.utils.formatters;

public class Formatters
{
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
