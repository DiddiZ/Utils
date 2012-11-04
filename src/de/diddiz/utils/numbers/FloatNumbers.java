package de.diddiz.utils.numbers;

public class FloatNumbers
{
	public static FloatNumber fixedIntNumber(float value) {
		return new FloatNumber(value) {
			@Override
			public void next() {}
		};
	}
}
