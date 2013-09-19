package de.diddiz.utils.numbers;

import static de.diddiz.utils.Utils.RANDOM;
import java.util.Random;

public final class FloatNumbers
{
	public static FloatNumber randomFloatNumber(float lowerBound, float upperBound) {
		return new RandomFloatNumber(lowerBound, upperBound, RANDOM);
	}

	public static FloatNumber randomFloatNumber(float lowerBound, float upperBound, Random random) {
		return new RandomFloatNumber(lowerBound, upperBound, random);
	}

	public static FloatNumber staticFloatNumber(float value) {
		return new StaticFloatNumber(value);
	}

	private static class RandomFloatNumber extends FloatNumber
	{
		private final float lowerBound, upperBound;
		private final Random random;

		private RandomFloatNumber(float lowerBound, float upperBound, Random random) {
			super(lowerBound + random.nextFloat() * (upperBound - lowerBound));
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.random = random;
		}

		@Override
		public void next() {
			value = lowerBound + random.nextFloat() * (upperBound - lowerBound);
		}
	}

	private static class StaticFloatNumber extends FloatNumber
	{
		public StaticFloatNumber(float value) {
			super(value);
		}

		@Override
		public void next() {}
	}
}
