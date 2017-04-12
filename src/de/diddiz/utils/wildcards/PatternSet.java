package de.diddiz.utils.wildcards;

/**
 * @author Robin Kupper
 */
public abstract class PatternSet
{
	/**
	 * Reserved constructor.
	 */
	PatternSet() {}

	/**
	 * @return true if the text matches all patterns.
	 */
	public abstract boolean matchesAll(String text);

	/**
	 * @return true if the text matches at least one pattern.
	 */
	public abstract boolean matchesAny(String text);
}
