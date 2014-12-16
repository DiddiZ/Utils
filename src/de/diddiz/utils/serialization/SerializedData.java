package de.diddiz.utils.serialization;

import static de.diddiz.utils.Utils.toBoolean;
import static de.diddiz.utils.Utils.toDouble;
import static de.diddiz.utils.Utils.toFloat;
import static de.diddiz.utils.Utils.toInt;
import static de.diddiz.utils.Utils.toLong;
import java.util.ArrayList;
import java.util.List;
import de.diddiz.utils.Utils;
import de.diddiz.utils.math.NotANumberException;

public abstract class SerializedData<K, V>
{
	/**
	 * Looks up the value assigned with the specified key. May return {@code null}.
	 */
	public abstract V get(K key);

	public boolean getBoolean(K key) throws SerializedDataException {
		try {
			return toBoolean(get(key));
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read data key '" + key + "': " + ex.getMessage());
		}
	}

	public boolean getBoolean(K key, boolean def) {
		return toBoolean(get(key), def);
	}

	/**
	 * @return Never {@code null}.
	 * @throws SerializedDataException If {@link #get(Object)} return {@code null}.
	 */
	public V getChecked(K key) throws SerializedDataException {
		final V value = get(key);
		if (value != null)
			return value;
		throw new SerializedDataException("Failed to read data key '" + key + "': Key not present");
	}

	public double getDouble(K key) throws SerializedDataException {
		try {
			return toDouble(getChecked(key));
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read key '" + key + "': " + ex.getMessage());
		}
	}

	public double getDouble(K key, double def) {
		return toDouble(get(key), def);
	}

	public float getFloat(K key) throws SerializedDataException {
		try {
			return toFloat(getChecked(key));
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read data key '" + key + "': " + ex.getMessage());
		}
	}

	public float getFloat(K key, float def) {
		return toFloat(get(key), def);
	}

	/** Returned {@link List} may contain {@code nulls}. */
	public List<Float> getFloatList(K key) throws SerializedDataException {
		try {
			final List<V> list = getList(key);
			final List<Float> ret = new ArrayList<>(list.size());
			for (final V value : list)
				ret.add(value != null ? toFloat(value) : null);
			return ret;
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read key '" + key + "': " + ex.getMessage());
		}
	}

	public int getInt(K key) throws SerializedDataException {
		try {
			return toInt(getChecked(key));
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read data key '" + key + "': " + ex.getMessage());
		}
	}

	public int getInt(K key, int def) {
		return toInt(get(key), def);
	}

	/** Returned {@link List} may contain {@code nulls}. */
	public List<Integer> getIntList(K key) throws SerializedDataException {
		try {
			final List<V> list = getList(key);
			final List<Integer> ret = new ArrayList<>(list.size());
			for (final V value : list)
				ret.add(value != null ? toInt(value) : null);
			return ret;
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read key '" + key + "': " + ex.getMessage());
		}
	}

	/**
	 * Returned {@link List} is assumed to be immutable and may contain {@code nulls}.
	 * <p>
	 * Never returns {@code null}. An empty {@link List} is returned instead.
	 *
	 * @throws SerializedDataException If key doesn't exist.
	 */
	public abstract List<V> getList(K key) throws SerializedDataException;

	public long getLong(K key) throws SerializedDataException {
		try {
			return toLong(getChecked(key));
		} catch (final NotANumberException ex) {
			throw new SerializedDataException("Failed to read data key '" + key + "': " + ex.getMessage());
		}
	}

	public long getLong(K key, long def) {
		return toLong(get(key), def);
	}

	public String getString(K key) throws SerializedDataException {
		return Utils.toString(getChecked(key));
	}

	public String getString(K key, String def) {
		try {
			return Utils.toString(getChecked(key));
		} catch (final SerializedDataException ex) {
			return def;
		}
	}

	/** Returned {@link List} may contain {@code nulls}. */
	public List<String> getStringList(K key) throws SerializedDataException {
		final List<V> list = getList(key);
		final List<String> ret = new ArrayList<>(list.size());
		for (final V value : list)
			ret.add(value != null ? Utils.toString(value) : null);
		return ret;
	}

	/**
	 * Tries to return {@link #getStringList(K)}.
	 *
	 * @param def List to return in case of a {@link SerializedDataException}
	 */
	public List<String> getStringList(K key, List<String> def) {
		try {
			return getStringList(key);
		} catch (final SerializedDataException ex) {
			return def;
		}
	}

	/**
	 * @return Whether the specific key is present and has any value attached.
	 */
	public boolean hasKey(K key) {
		return get(key) != null;
	}

	/**
	 * @return Whether all specified keys are present and have a value attached.
	 */
	@SuppressWarnings("unchecked")
	public boolean hasKeys(K... keys) {
		for (final K key : keys)
			if (get(key) == null)
				return false;
		return true;
	}
}
