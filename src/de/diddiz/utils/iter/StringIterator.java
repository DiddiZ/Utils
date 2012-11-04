package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringIterator implements Iterable<Character>
{
	private final CharSequence chars;

	public StringIterator(CharSequence chars) {
		this.chars = chars;
	}

	@Override
	public Iterator<Character> iterator() {
		return new Iterator<Character>() {
			private final int len = chars.length();
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < len;
			}

			@Override
			public Character next() throws NoSuchElementException {
				if (pos < len)
					return chars.charAt(pos++);
				throw new NoSuchElementException();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
