package de.diddiz.utils.collections;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Random;

public class RandomSet<T>
{
	private static final Random rnd = new Random();
	private final T[] values;
	private final int size;
	private T last;

	@SuppressWarnings("unchecked")
	public RandomSet(Collection<T> col, Class<T> clazz) {
		this(col.toArray((T[])Array.newInstance(clazz, col.size())));
	}

	@SafeVarargs
	public RandomSet(T... arr) {
		if (arr.length == 0)
			throw new IllegalArgumentException("Must provide at least one value");
		size = arr.length;
		values = arr;
	}

	public T get() {
		last = values[rnd.nextInt(size)];
		return last;
	}

	public T getNotLast() {
		T cur = values[rnd.nextInt(size)];
		if (values.length > 1)
			while (cur == last)
				cur = values[rnd.nextInt(size)];
		last = cur;
		return cur;
	}

	public T[] getValues() {
		return values;
	}
}
