package de.diddiz.utils.predicates;

public class CharPredicates
{
	/**
	 * Allows the numbers from {@code 0} to {@code 9} as well as dots ({@code .}) and signs ({@code +-}).
	 */
	public static CharPredicate decimals() {
		return DecimalCharPredicate.INSTANCE;
	}

	/**
	 * Allows the numbers from {@code 0} to {@code 9}.
	 */
	public static CharPredicate digitsOnly() {
		return DigitCharPredicate.INSTANCE;
	}

	private static class DecimalCharPredicate implements CharPredicate
	{
		public static final CharPredicate INSTANCE = new DecimalCharPredicate();

		@Override
		public boolean apply(char input) {
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
				default:
					return false;
			}
		}
	}

	private static class DigitCharPredicate implements CharPredicate
	{
		public static final CharPredicate INSTANCE = new DigitCharPredicate();

		@Override
		public boolean apply(char input) {
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
				default:
					return false;
			}
		}
	}
}
