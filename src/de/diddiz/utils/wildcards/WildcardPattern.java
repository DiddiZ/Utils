package de.diddiz.utils.wildcards;

import java.util.function.Predicate;

public abstract class WildcardPattern implements Predicate<String>
{
	/**
	 * Computes whether the text matches the pattern.
	 *
	 * Only supported wildcard character is {@code *}.
	 *
	 * All checks are case sensitive.
	 */
	public abstract boolean matches(String text);

	@Override
	public final boolean test(String input) {
		return matches(input);
	}
}
