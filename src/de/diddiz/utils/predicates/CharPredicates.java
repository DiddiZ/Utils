package de.diddiz.utils.predicates;

import com.google.common.base.Predicate;

public final class CharPredicates
{
	/**
	 * Allows the numbers from {@code 0} to {@code 9} as well as dots ({@code .}) and signs ({@code +-}).
	 */
	public static Predicate<Character> decimals() {
		return DecimalCharPredicate.INSTANCE;
	}

	/**
	 * Allows the numbers from {@code 0} to {@code 9}.
	 */
	public static Predicate<Character> digitsOnly() {
		return DigitCharPredicate.INSTANCE;
	}

	private static class DecimalCharPredicate implements Predicate<Character>
	{
		private static final Predicate<Character> INSTANCE = new DecimalCharPredicate();

		@Override
		public boolean apply(Character input) {
			if (input != null)
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
		}
	}

	private static class DigitCharPredicate implements Predicate<Character>
	{
		private static final Predicate<Character> INSTANCE = new DigitCharPredicate();

		@Override
		public boolean apply(Character input) {
			if (input != null)
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
		}
	}
}
