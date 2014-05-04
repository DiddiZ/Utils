package de.diddiz.utils.wildcards;

import static de.diddiz.utils.Utils.join;
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
		else if (wildcards == 2 && pattern.charAt(0) == '*' && pattern.charAt(pattern.length() - 1) == '*')
			return new ContainsPattern(pattern.substring(1, pattern.length() - 1));
		return new MultiWildcardPattern(pattern);
	}

	/**
	 * Simple pattern that checks if the supplied text contains {@code str}.
	 */
	private static class ContainsPattern extends WildcardPattern
	{
		private final String str;

		public ContainsPattern(String str) {
			this.str = str;
		}

		@Override
		public boolean matches(String text) {
			return text.indexOf(str) > -1;
		}

		@Override
		public String toString() {
			return "*" + str + "*";
		}
	}

	/**
	 * Simple pattern that checks if the supplied text matches the suffix.
	 */
	private static class EndsWithPattern extends WildcardPattern
	{
		private final String suffix;

		public EndsWithPattern(String suffix) {
			this.suffix = suffix;
		}

		@Override
		public boolean matches(String text) {
			return text.endsWith(suffix);
		}

		@Override
		public String toString() {
			return "*" + suffix;
		}
	}

	/**
	 * Simple pattern that checks equality.
	 */
	private static class EqualsPattern extends WildcardPattern
	{
		private final String equal;

		public EqualsPattern(String equal) {
			this.equal = equal;
		}

		@Override
		public boolean matches(String text) {
			return equal.equals(text);
		}

		@Override
		public String toString() {
			return equal;
		}
	}

	/**
	 * Simple pattern that returns true.
	 */
	private static class EverythingPattern extends WildcardPattern
	{
		public static final WildcardPattern INSTANCE = new EverythingPattern();

		@Override
		public boolean matches(String text) {
			return true;
		}

		@Override
		public String toString() {
			return "*";
		}
	}

	private static class MultiWildcardPattern extends WildcardPattern
	{
		private final String[] cards;
		private final boolean startsWithWildcard, endsWithWildcard;

		public MultiWildcardPattern(String pattern) {
			cards = pattern.split("\\*");
			startsWithWildcard = pattern.startsWith("*");
			endsWithWildcard = pattern.endsWith("*");
		}

		@Override
		public boolean matches(String text) {
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

		@Override
		public String toString() {
			String str = join(cards, '*');
			if (startsWithWildcard)
				str = "*" + str;
			if (endsWithWildcard)
				str += "*";
			return str;
		}
	}

	/**
	 * Simple pattern that checks if the supplied text matches the prefix and the suffix.
	 */
	private static class StartsEndsWithPattern extends WildcardPattern
	{
		private final String prefix, suffix;

		public StartsEndsWithPattern(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}

		@Override
		public boolean matches(String text) {
			return text.startsWith(prefix) && text.endsWith(suffix);
		}

		@Override
		public String toString() {
			return prefix + "*" + suffix;
		}
	}

	/**
	 * Simple pattern that checks if the supplied text matches the prefix .
	 */
	private static class StartsWithPattern extends WildcardPattern
	{
		private final String prefix;

		public StartsWithPattern(String prefix) {
			this.prefix = prefix;
		}

		@Override
		public boolean matches(String text) {
			return text.startsWith(prefix);
		}

		@Override
		public String toString() {
			return prefix + "*";
		}
	}
}
