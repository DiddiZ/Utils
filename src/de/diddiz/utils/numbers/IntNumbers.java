package de.diddiz.utils.numbers;

public final class IntNumbers
{
	public static IntNumber fixedIntNumber(int value) {
		return new IntNumber(value) {
			@Override
			public void next() {}
		};
	}
}
