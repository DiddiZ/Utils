package de.diddiz.utils.math;

import java.util.Map;

/**
 * Simple class representing an integer counter.
 * <p>
 * This class is intended to be used in {@link Map Maps} as value in place of an Integer to avoid using {@link Map#put(Object, Object) put} for every value update.
 *
 * <pre>
 * Map&lt;String, Integer&gt; map = new HashMap&lt;&gt;();
 * Integer value = map.get(key);
 * map.put(key, value != null ? value + 1 : 1);
 * </pre>
 *
 * <pre>
 * final Map&lt;String, Counter&gt; map = new HashMap&lt;&gt;();
 * final Counter counter = map.get(key);
 * if (counter == null) {
 * 	counter = new Counter(0);
 * 	map.put(key, counter);
 * }
 * counter.increment();
 * </pre>
 */
public class Counter
{
	private int value;

	public Counter() {}

	public Counter(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void increment() {
		value++;
	}

	public void increment(int increment) {
		value += increment;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
