package de.diddiz.utils.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CharSequenceIterator implements Iterable<Character>
{
	private final CharSequence chars;

	public CharSequenceIterator(CharSequence chars) {
		this.chars = chars;
	}

	@Override
	public Iterator<Character> iterator() {
		return new Iterator<Character>() {
			private int pos = 0;

			@Override
			public boolean hasNext() {
				return pos < chars.length();
			}

			@Override
			public Character next() throws NoSuchElementException {
				if (pos < chars.length())
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
