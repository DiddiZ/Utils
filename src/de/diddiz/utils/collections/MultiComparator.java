package de.diddiz.utils.collections;

import java.util.Comparator;

/**
 * MultiComparator is used to chain multiple comparators.
 * If the first comparator returns 0 (i.e. equal), the second is tried, and so on.
 * Comparators are tired in order.
 * Return 0 only of all comparators return 0. Otherwise this returns the first non-zero return value.
 *
 * @author Robin Kupper
 */
public class MultiComparator<T> implements Comparator<T>
{
	private final Comparator<T>[] comparators;

	@SafeVarargs
	public MultiComparator(Comparator<T>... comparators) {
		this.comparators = comparators;
	}

	@Override
	public int compare(T o1, T o2) {
		int cmp;
		for (int i = 0; i < comparators.length - 1; i++)
			if ((cmp = comparators[i].compare(o1, o2)) != 0)
				return cmp;
		return comparators[comparators.length - 1].compare(o1, o2);
	}
}
