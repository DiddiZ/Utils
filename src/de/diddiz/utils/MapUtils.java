package de.diddiz.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

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
	 * Generates the values and keys from a third variable by a function and builds an immutable map.
	 */
	public static <K, V, T> Map<K, V> immutableMapOfEntryFactory(Iterable<T> iter, Function<T, Entry<K, V>> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final T t : iter)
			builder.put(function.apply(t));
		return builder.build();
	}

	/**
	 * Generates the values and keys from a third variable by a function and builds an immutable map.
	 */
	public static <K, V, T> Map<K, V> immutableMapOfEntryFactory(T[] iter, Function<T, Entry<K, V>> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final T t : iter)
			builder.put(function.apply(t));
		return builder.build();
	}

	/**
	 * Generates the values for a keys by a function and builds an immutable map.
	 */
	public static <K, V> ImmutableMap<K, V> immutableMapOfKeys(Iterable<K> keys, Function<K, V> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final K k : keys)
			builder.put(k, function.apply(k));
		return builder.build();
	}

	/**
	 * Generates the values for a keys by a function and builds an immutable map.
	 */
	public static <K, V> ImmutableMap<K, V> immutableMapOfKeys(K[] keys, Function<K, V> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final K k : keys)
			builder.put(k, function.apply(k));
		return builder.build();
	}

	/**
	 * Generates the keys for a values by a function and builds an immutable map.
	 */
	public static <K, V> ImmutableMap<K, V> immutableMapOfValues(Iterable<V> values, Function<V, K> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final V v : values)
			builder.put(function.apply(v), v);
		return builder.build();
	}

	/**
	 * Generates the keys for a values by a function and builds an immutable map.
	 */
	public static <K, V> ImmutableMap<K, V> immutableMapOfValues(V[] values, Function<V, K> function) {
		final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
		for (final V v : values)
			builder.put(function.apply(v), v);
		return builder.build();
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
