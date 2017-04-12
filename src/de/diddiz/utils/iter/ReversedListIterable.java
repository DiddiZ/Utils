package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Iterates a list in reversed order. Doesn't support remove or changes to this list.
 * 
 * @author Robin Kupper
 */
public class ReversedListIterable<T> implements Iterable<T>
{
	private final List<T> list;

	public ReversedListIterable(List<T> list) {
		this.list = list;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int pos = list.size() - 1;

			@Override
			public boolean hasNext() {
				return pos >= 0;
			}

			@Override
			public T next() throws NoSuchElementException {
				if (pos >= 0)
					return list.get(pos--);
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
