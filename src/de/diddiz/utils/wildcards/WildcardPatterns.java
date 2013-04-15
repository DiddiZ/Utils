package de.diddiz.utils.wildcards;

import de.diddiz.utils.Utils;

public final class WildcardPatterns
{
	/**
	 * Has various optimizations for patterns with no or just one wildcard.
	 * 
	 * @param text {@code String} with an arbitrary number of wildcards (*).
	 * 
	 * @see WildcardPattern
	 */
	public static WildcardPattern compile(String pattern) {
		final int wildcards = Utils.countOccurrences(pattern, '*');
		if (wildcards == 0) // The pattern contains no wildcard, we can use equals to match.
			return new EqualsPattern(pattern);
		else if (wildcards == 1)
			if (pattern.length() == 1) // Pattern exists only of the wildcard.
				return EverythingPattern.INSTANCE;
			else if (pattern.startsWith("*")) { //
				final String suffix = pattern.substring(1);
				return new EndsWithPattern(suffix);
			} else if (pattern.endsWith("*")) {
				final String prefix = pattern.substring(0, pattern.length() - 1);
				return new StartsWithPattern(prefix);
			} else {
				final int idx = pattern.indexOf('*');
				final String prefix = pattern.substring(0, idx);
				final String suffix = pattern.substring(idx + 1);
				return new StartsEndsWithPattern(prefix, suffix);
			}
		return new MultiWildcardPattern(pattern);
	}

	/**
	 * @return true if the text matches all patterns.
	 */
	public static boolean matchAll(Iterable<WildcardPattern> patterns, String text) {
		for (final WildcardPattern pattern : patterns)
			if (!pattern.match(text))
				return false;
		return true;
	}

	/**
	 * @return true if the text matches at least one pattern.
	 */
	public static boolean matchAny(Iterable<WildcardPattern> patterns, String text) {
		for (final WildcardPattern pattern : patterns)
			if (pattern.match(text))
				return true;
		return false;
	}

	/**
	 * Simple pattern that checks if the supplied text matches the suffix.
	 */
	private static class EndsWithPattern implements WildcardPattern
	{
		private final String suffix;

		public EndsWithPattern(String suffix) {
			this.suffix = suffix;
		}

		@Override
		public boolean match(String text) {
			return text.endsWith(suffix);
		}
	}

	/**
	 * Simple pattern that checks equality.
	 */
	private static class EqualsPattern implements WildcardPattern
	{
		private final String equal;

		public EqualsPattern(String equal) {
			this.equal = equal;
		}

		@Override
		public boolean match(String text) {
			return equal.equals(text);
		}
	}

	/**
	 * Simple pattern that returns true.
	 */
	private static class EverythingPattern implements WildcardPattern
	{
		public static final WildcardPattern INSTANCE = new EverythingPattern();

		@Override
		public boolean match(String text) {
			return true;
		}
	}

	private static class MultiWildcardPattern implements WildcardPattern
	{
		private final String[] cards;
		private final boolean startsWithWildcard, endsWithWildcard;

		public MultiWildcardPattern(String pattern) {
			cards = pattern.split("\\*");
			startsWithWildcard = pattern.startsWith("*");
			endsWithWildcard = pattern.endsWith("*");
		}

		@Override
		public boolean match(String text) {
			int pos = 0;

			if (!startsWithWildcard) { // Make sure it starts with the first card
				if (!text.startsWith(cards[0]))
					return false;
				pos = cards[0].length();
			}

			if (!endsWithWildcard)// Make sure it ends with the last card
				if (!text.endsWith(cards[cards.length - 1]))
					return false;

			// We can skip the first or last card if we checked these already
			final int startCard = startsWithWildcard ? 0 : 1, endCard = endsWithWildcard ? cards.length : cards.length - 1;

			for (int i = startCard; i < endCard; i++) {
				final int idx = text.indexOf(cards[i], pos);

				// Card not detected in the text.
				if (idx == -1)
					return false;

				// Move ahead, towards the right of the text.
				pos = idx + cards[i].length();
			}

			return true;
		}
	}

	/**
	 * Simple pattern that checks if the supplied text matches the prefix and the suffix.
	 */
	private static class StartsEndsWithPattern implements WildcardPattern
	{
		private final String prefix, suffix;

		public StartsEndsWithPattern(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}

		@Override
		public boolean match(String text) {
			return text.startsWith(prefix) && text.endsWith(suffix);
		}
	}

	/**
	 * Simple pattern that checks if the supplied text matches the prefix .
	 */
	private static class StartsWithPattern implements WildcardPattern
	{
		private final String prefix;

		public StartsWithPattern(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public boolean match(String text) {
			return text.startsWith(prefix);
		}
	}
}
