package de.diddiz.utils.wildcards;

public interface PatternSet
{
	/**
	 * @return true if the text matches all patterns.
	 */
	public boolean matchAll(String text);

	/**
	 * @return true if the text matches at least one pattern.
	 */
	public boolean matchAny(String text);
}
