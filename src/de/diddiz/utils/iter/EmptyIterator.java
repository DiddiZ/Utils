package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EmptyIterator<T> implements Iterable<T>, Iterator<T>
{
	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}

	@Override
	public T next() {
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
