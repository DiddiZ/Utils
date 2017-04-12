package de.diddiz.utils.modifiers;

public interface Modifier
{
	/**
	 * Accepts a float in the range of {@code 0..1} and returns a float from {@code 0..1}.
	 * May work outside of that range, but this isn't guaranteed.
	 */
	public float modify(float factor);
}
