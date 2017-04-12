package de.diddiz.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import de.diddiz.utils.Utils;

/**
 * @author Robin Kupper
 */
public class RandomSet<T> implements Iterable<T>, Supplier<T>
{
	private final List<T> values;
	private final Random rnd;
	private final int size;
	private T last;

	/**
	 * Creates a new {@code RandomSet} using {@link Utils.RANDOM} as randomizer.
	 */
	public RandomSet(Collection<T> col) {
		this(col, Utils.RANDOM);
	}

	/**
	 * Creates a new {@code RandomSet} with a custom randomizer.
	 */
	public RandomSet(Collection<T> col, Random rnd) {
		if (col.size() == 0)
			throw new IllegalArgumentException("Must provide at least one value");
		values = new ArrayList<>(col);
		this.rnd = rnd;
		size = values.size();
	}

	/**
	 * Returns a random element
	 */
	@Override
	public T get() {
		return last = values.get(rnd.nextInt(size));
	}

	/**
	 * Returns a random element that isn't the same as returned by the last call of {@link #getRandom()} or {@link #getRandomNotLast()}, provided this {@code RandomSet} contains more than one element.
	 */
	public T getNotLast() {
		T cur = values.get(rnd.nextInt(size));
		if (size > 1) // If the set has just one element, we have to return the same anyways.
			while (cur == last)
				cur = values.get(rnd.nextInt(size));
		return last = cur;
	}

	/**
	 * Returns a unmodifiable iterator
	 */
	@Override
	public Iterator<T> iterator() {
		return Collections.unmodifiableList(values).iterator();
	}
}
