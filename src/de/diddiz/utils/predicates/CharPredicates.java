package de.diddiz.utils.predicates;

public class CharPredicates
{
	public static CharPredicate digitsOnly() {
		return new CharPredicate() {
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
				}
				return false;
			}
		};
	}
}
