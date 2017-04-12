package de.diddiz.utils.predicates;

import java.util.Set;
import java.util.function.IntPredicate;

/**
 * @author Robin Kupper
 */
public final class CharPredicates
{
	/**
	 * Accepts if the char is contained in the set
	 */
	public static IntPredicate charFilter(Set<Character> allowedChars) {
		return input -> allowedChars.contains((char)input);
	}

	/**
	 * Allows the numbers from {@code 0} to {@code 9} as well as dots ({@code .}) and signs ({@code +-}).
	 */
	public static IntPredicate decimals() {
		return input -> {
			switch (input) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case '.':
				case '+':
				case '-':
					return true;
			}
			return false;
		};
	}

	/**
	 * Allows the numbers from {@code 0} to {@code 9}.
	 */
	public static IntPredicate digitsOnly() {
		return input -> {
			switch (input) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					return true;
			}
			return false;
		};
	}
}
