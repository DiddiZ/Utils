package de.diddiz.utils.wildcards;

public interface WildcardPattern
{
	/**
	 * Computes whether the text matches the pattern.
	 * 
	 * Only supported wildcard character is {@code *}.
	 * 
	 * All checks are case sensitive.
	 */
	public boolean match(String text);
}
