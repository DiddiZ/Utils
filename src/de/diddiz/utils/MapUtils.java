package de.diddiz.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class MapUtils
{
	/**
	 * Puts the content of all maps to a new {@link LinkedHashMap}.
	 * 
	 * This will override same keys.
	 * 
	 * @see #putAll(Map, Iterable)
	 */
	public static <K, V> Map<K, V> combine(Iterable<Map<K, V>> maps) {
		int size = 0;
		for (final Map<K, V> m : maps)
			size += m.size();
		return putAll(new LinkedHashMap<K, V>(size), maps);
	}

	/**
	 * Puts the content of multiple maps to a map.
	 * 
	 * This will override same keys.
	 */
	public static <K, V> Map<K, V> putAll(Map<K, V> map, Iterable<Map<K, V>> maps) {
		for (final Map<K, V> m : maps)
			map.putAll(m);
		return map;
	}
}
