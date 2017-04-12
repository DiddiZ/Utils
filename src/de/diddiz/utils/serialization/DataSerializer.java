package de.diddiz.utils.serialization;

import java.util.Map;

/**
 * Allows mass-serialization of classes that can't implement {@link DataSerializable}.
 *
 * @author Robin Kupper
 */
public interface DataSerializer<T>
{
	/**
	 * Object can be any type of {@code List}, {@code Map}, {@code String} and primitive wrapper.
	 */
	public Map<String, Object> serialize(T t);
}
