package de.diddiz.utils.wildcards;

import com.google.common.base.Predicate;

public abstract class WildcardPattern implements Predicate<String>
{
	@Override
	public final boolean apply(String input) {
		return match(input);
	}

	/**
	 * Computes whether the text matches the pattern.
	 * 
	 * Only supported wildcard character is {@code *}.
	 * 
	 * All checks are case sensitive.
	 */
	public abstract boolean match(String text);
}
