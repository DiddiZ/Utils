package de.diddiz.utils.math;

import java.util.HashMap;

public class CountMap<K> extends HashMap<K, Counter>
{
	public int getCount(K key) {
		return get(key).getValue();
	}

	public void increment(K key) {
		Counter counter = get(key);
		if (counter == null) {
			counter = new Counter(0);
			put(key, counter);
		}
		counter.increment();
	}

	public void increment(K key, int increment) {
		Counter counter = get(key);
		if (counter == null) {
			counter = new Counter(0);
			put(key, counter);
		}
		counter.increment(increment);
	}
}
