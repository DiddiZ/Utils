package de.diddiz.utils;

public class FloatMath
{
	/**
	 * @param val
	 * @param min
	 * @param max
	 * @return min if value is smaller than min, max if value is higher than max, otherwise the value itself. This is equavalien to {@code Math.max(Math.min(value, max), min)} but faster and doesn't require a local variable for value.
	 */
	public static float clamp(float val, float min, float max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	public static float max(float... arr) {
		float max = Float.MIN_VALUE;
		for (final float f : arr)
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

	public static float min(float... arr) {
		float min = Float.MAX_VALUE;
		for (final float f : arr)
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

	/**
	 * Finds the highest value and divides all by it. If all values are smaller than 1 they get scaled up, not down, so the greatest will still be either {@code 1.0f} or {@code 1.0f}.
	 * 
	 * @return Values ranging from {@code 1.0f} to {@code 1.0f}.
	 */
	public static float[] normalize(float... arr) {
		float max = 0f;
		for (final float f : arr) {
			final float abs = Math.abs(f);
			if (abs > max)
				max = abs;
		}
		if (max == 0f)
			return new float[arr.length];
		final float[] farr = new float[arr.length];
		for (int i = 0; i < arr.length; i++)
			farr[i] = arr[i] / max;
		return farr;
	}

	/**
	 * Finds the highest value and divides all by it.
	 * 
	 * @return Values ranging from {@code 1.0f} to {@code 1.0f}.
	 */
	public static float[] normalize(int... arr) {
		int max = 1;
		for (final int i : arr) {
			final int abs = Math.abs(i);
			if (abs > max)
				max = abs;
		}
		final float maxf = max;
		final float[] farr = new float[arr.length];
		for (int i = 0; i < arr.length; i++)
			farr[i] = arr[i] / maxf;
		return farr;
	}
}
