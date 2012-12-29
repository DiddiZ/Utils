package de.diddiz.utils;

public class FloatMath
{
	/**
	 * @param val
	 * @param min
	 * @param max
	 * @return min if value is smaller than min, max if value is higher than max, otherwise the value itself. This is equavalien to {@code Math.max(Math.min(value, max), min)} but faster and doesn't require a local variable for value.
	 */
	public static float fence(float val, float min, float max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	public static float max(float... args) {
		float max = Float.MIN_VALUE;
		for (final float f : args)
			if (f > max)
				max = f;
		return max;
	}

	public static float max(float a, float b, float c) {
		if (a > b) {
			if (a > c)
				return a;
			return b > c ? b : c;
		}
		if (b > c)
			return b;
		return a > c ? a : c;
	}

	public static float min(float... args) {
		float min = Float.MAX_VALUE;
		for (final float f : args)
			if (f < min)
				min = f;
		return min;
	}

	public static float min(float a, float b, float c) {
		if (a < b) {
			if (a < c)
				return a;
			return b < c ? b : c;
		}
		if (b < c)
			return b;
		return a < c ? a : c;
	}
}
