package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterable<T>
{
	private final T arr[];

	public ArrayIterator(T[] array) {
		this.arr = array;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < arr.length;
			}

			@Override
			public T next() throws NoSuchElementException {
				if (pos < arr.length)
					return arr[pos++];
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
