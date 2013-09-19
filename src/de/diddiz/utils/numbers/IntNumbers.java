package de.diddiz.utils.numbers;

import static de.diddiz.utils.Utils.RANDOM;
import java.util.Random;

public final class IntNumbers
{
	public static IntNumber randomIntNumber(int lowerBound, int upperBound) {
		return new RandomIntNumber(lowerBound, upperBound, RANDOM);
	}

	public static IntNumber randomIntNumber(int lowerBound, int upperBound, Random random) {
		return new RandomIntNumber(lowerBound, upperBound, random);
	}

	public static IntNumber staticIntNumber(int value) {
		return new StaticIntNumber(value);
	}

	private static class RandomIntNumber extends IntNumber
	{
		private final int lowerBound, upperBound;
		private final Random random;

		private RandomIntNumber(int lowerBound, int upperBound, Random random) {
			super(lowerBound + random.nextInt(upperBound - lowerBound + 1));
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.random = random;
		}

		@Override
		public void next() {
			value = lowerBound + random.nextInt(upperBound - lowerBound + 1);
		}
	}

	private static class StaticIntNumber extends IntNumber
	{
		public StaticIntNumber(int value) {
			super(value);
		}

		@Override
		public void next() {}
	}
}
