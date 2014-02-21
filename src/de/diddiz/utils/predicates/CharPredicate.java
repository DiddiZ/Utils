package de.diddiz.utils.predicates;

import com.google.common.base.Predicate;

/**
 * Primitive wrapper for char predicates
 */
public abstract class CharPredicate implements Predicate<Character>
{
	public abstract boolean apply(char input);

	@Override
	public final boolean apply(Character input) {
		if (input != null)
			return apply((char)input);
		return false;
	}
}