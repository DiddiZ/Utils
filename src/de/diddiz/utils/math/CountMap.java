package de.diddiz.utils.math;

import java.util.HashMap;

/**
 * @author Robin Kupper
 */
public class CountMap<K> extends HashMap<K, Counter>
{
	public int getCount(K key) {
		return get(key).getValue();
	}

	public void increment(K key) {
		getCounter(key).increment();
	}

	public void increment(K key, int increment) {
		getCounter(key).increment(increment);
	}

	private Counter getCounter(K key) {
		Counter counter = get(key);
		if (counter == null) {
			counter = new Counter();
			put(key, counter);
		}
		return counter;
	}
}
