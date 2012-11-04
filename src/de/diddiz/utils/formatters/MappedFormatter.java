package de.diddiz.utils.formatters;

import java.util.Map;

public class MappedFormatter<T> implements Formatter<T>
{
	private final Map<T, String> map;

	public MappedFormatter(Map<T, String> map) {
		this.map = map;
	}

	@Override
	public String format(T t) {
		final String str = map.get(t);
		return str != null ? str : t.toString();
	}
}
