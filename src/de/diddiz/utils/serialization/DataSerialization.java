package de.diddiz.utils.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class DataSerialization
{
	/**
	 * Serializes a list of {@code DataSerializables}.
	 * <p>
	 * Supplied collection may contains nulls.
	 */
	public static List<Map<String, Object>> serialize(Collection<? extends DataSerializable> serializables) {
		final List<Map<String, Object>> list = new ArrayList<>(serializables.size());
		for (final DataSerializable serializable : serializables)
			if (serializable != null)
				list.add(serializable.serialize());
		return list;
	}

	/**
	 * Serializes a list of generic objects using a {@link DataSerializer}.
	 * <p>
	 * Supplied collection may contains nulls.
	 */
	public static <T> List<Map<String, Object>> serialize(Collection<T> objects, DataSerializer<T> serializer) {
		final List<Map<String, Object>> list = new ArrayList<>(objects.size());
		for (final T t : objects)
			if (t != null)
				list.add(serializer.serialize(t));
		return list;
	}

	/**
	 * Serializes a list of {@code DataSerializables}.
	 * <p>
	 * Supplied array may contains nulls.
	 */
	public static <T extends DataSerializable> List<Map<String, Object>> serialize(T[] serializables) {
		final List<Map<String, Object>> list = new ArrayList<>(serializables.length);
		for (final DataSerializable serializable : serializables)
			if (serializable != null)
				list.add(serializable.serialize());
		return list;
	}

	/**
	 * Serializes a list of generic objects using a {@link DataSerializer}.
	 * <p>
	 * Supplied array may contains nulls.
	 */
	public static <T> List<Map<String, Object>> serialize(T[] objects, DataSerializer<T> serializer) {
		final List<Map<String, Object>> list = new ArrayList<>(objects.length);
		for (final T t : objects)
			if (t != null)
				list.add(serializer.serialize(t));
		return list;
	}

	/**
	 * Serializes the values of a map while keeping the key mapping.
	 */
	public static Map<String, Object> serializeMap(Map<String, ? extends DataSerializable> serializables) {
		final Map<String, Object> map = new LinkedHashMap<>(serializables.size());
		for (final Entry<String, ? extends DataSerializable> e : serializables.entrySet())
			map.put(e.getKey(), e.getValue().serialize());
		return map;
	}

	/**
	 * Serializes the values of a map while keeping the key mapping using a {@link DataSerializer}..
	 */
	public static <T> Map<String, Object> serializeMap(Map<String, T> objectMap, DataSerializer<T> serializer) {
		final Map<String, Object> map = new LinkedHashMap<>(objectMap.size());
		for (final Entry<String, T> e : objectMap.entrySet())
			map.put(e.getKey(), serializer.serialize(e.getValue()));
		return map;
	}
}
