package de.diddiz.utils.predicates;

import java.util.function.Predicate;

/**
 * Primitive wrapper for char predicates
 */
public interface CharPredicate extends Predicate<Character>
{
	public boolean test(char input);

	@Override
	public default boolean test(Character input) {
		if (input != null)
			return test((char)input);
		return false;
	}
}