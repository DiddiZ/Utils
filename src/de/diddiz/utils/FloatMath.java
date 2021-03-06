package de.diddiz.utils;

/**
 * @author Robin Kupper
 */
public final class FloatMath
{
	/**
	 * Creates a new array with averaged values.
	 * <p>
	 * The radius determines how many adjected value are factored in.
	 * <p>
	 * Start and end are clamped.
	 *
	 * @throws IllegalArgumentException if {@code values} is empty
	 */
	public static float[] averages(float[] values, int radius) {
		if (values == null || values.length == 0)
			throw new IllegalArgumentException("Supplied array is empty");

		final float avg[] = new float[values.length];
		for (int i = 0; i < values.length; i++) {
			float sum = 0f;
			for (int j = i - radius; j <= i + radius; j++)
				sum += values[IntMath.clamp(j, 0, values.length - 1)];
			avg[i] = sum / (radius * 2 + 1);
		}
		return avg;
	}

	/**
	 * This is equivalent to {@code Math.max(Math.min(value, max), min)} but it's faster and doesn't require a local variable for value.
	 *
	 * @return min if value is smaller than min, max if value is higher than max, otherwise the value itself.
	 */
	public static float clamp(float val, float min, float max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	/**
	 * Computes and returns the largest (nearest to positive infinity) value.
	 *
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static float max(float... arr) {
		float max = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] > max)
				max = arr[i];
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

	/**
	 * Returns the index of the maximum value (closest to positive infinity).
	 * <p>
	 * Supplied array may contain <code>null</code> and {@link Float#NaN}.
	 * <p>
	 * Returns <code>-1</code> if array is empty, or only contains nulls or NaNs.
	 */
	public static int maxIdx(float[] arr) {
		int maxIdx = -1;
		float max = Float.NEGATIVE_INFINITY;
		for (int i = 0; i < arr.length; i++)
			if (arr[i] > max) {
				maxIdx = i;
				max = arr[i];
			}
		return maxIdx;
	}

	/**
	 * Computes and returns the smallest (nearest to negative infinity) value.
	 *
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static float min(float... arr) {
		float min = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] < min)
				min = arr[i];
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
	 * Returns a value between {@code f1} and {@code f2}, weighted by {@code factor}.
	 * <p>
	 * When {@code factor} equals {@code 0f}, {@code f1} will be returned, whereas {@code 1f} will return {@code f2}.
	 * <p>
	 * The value {@code factor} is generally assumed to be between {@code 0f} and {@code 1f}, but it'll also work for values outside this range.
	 */
	public static float mix(float f1, float f2, float factor) {
		return f1 + (f2 - f1) * factor;
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

	/**
	 * Computes the sum of a {@code float} array.
	 */
	public static float sum(float... floats) {
		float sum = 0f;
		for (final float f : floats)
			sum += f;
		return sum;
	}
}
