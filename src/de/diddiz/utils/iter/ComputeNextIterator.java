package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple {@link Iterator} that tries to compute the next value in order to check if it has more available.
 */
public abstract class ComputeNextIterator<T> implements Iterator<T>
{
	private T next = computeNext();

	@Override
	public final boolean hasNext() {
		return next != null;
	}

	@Override
	public final T next() {
		if (next == null)
			throw new NoSuchElementException();

		final T ret = next;
		next = computeNext();
		return ret;
	}

	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return either the next value or null to indicate we're done.
	 */
	protected abstract T computeNext();
}
