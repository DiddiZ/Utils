package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterable<T>, Iterator<T>
{
	private final T arr[];
	private int pos = 0;

	public ArrayIterator(T[] array) {
		this.arr = array;
	}

	@Override
	public boolean hasNext() {
		return pos < arr.length;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
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

	public void rewind() {
		pos = 0;
	}
}
