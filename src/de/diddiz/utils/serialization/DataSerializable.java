package de.diddiz.utils.serialization;

import java.util.Map;

/**
 * A class that can serialize itself to a String->Object map.
 * Deserializability isn't implicitly guaranteed.
 * 
 * @author Robin Kupper
 */
public interface DataSerializable
{
	/**
	 * Object can be any type of {@code List}, {@code Map}, {@code String} and primitive wrapper.
	 */
	public Map<String, Object> serialize();
}
