package de.diddiz.utils;

public class IntMath
{
	public static int ceil(double d) {
		final int ival = (int)d;
		if (d == ival)
			return ival;
		return ival + signum(d);
	}

	public static int ceil(float f) {
		final int ival = (int)f;
		if (f == ival)
			return ival;
		return ival + signum(f);
	}

	/**
	 * @param val
	 * @param min
	 * @param max
	 * @return min if value is smaller than min, max if value is higher than max, otherwise the value itself. This is equavalien to {@code Math.max(Math.min(value, max), min)} but faster and doesn't require a local variable for value.
	 */
	public static int clamp(int val, int min, int max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

	public static int floor(double val) {
		return (int)val;
	}

	public static int floor(float val) {
		return (int)val;
	}

	public static int indexOfMin(int[] arr) {
		int idx = 0;
		int min = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] < min) {
				min = arr[i];
				idx = i;
			}
		return idx;
	}

	/**
	 * Computes and returns the largest (nearest to positive infinity) value.
	 * 
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static float max(int... arr) {
		int max = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] > max)
				max = arr[i];
		return max;
	}

	public static int max(int a, int b, int c) {
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
	 * Computes and returns the smallest (nearest to negative infinity) value.
	 * 
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static int min(int... arr) {
		int min = Integer.MAX_VALUE;
		for (int i = 1; i < arr.length; i++)
			if (arr[i] < min)
				min = arr[i];
		return min;
	}

	public static int min(int a, int b, int c) {
		if (a < b) {
			if (a < c)
				return a;
			return b < c ? b : c;
		}
		if (b < c)
			return b;
		return a < c ? a : c;
	}

	public static int round(double d) {
		if (d > 0D)
			return (int)(d + 0.5);
		return (int)(d - 0.5);
	}

	public static int round(float f) {
		if (f > 0f)
			return (int)(f + 0.5f);
		return (int)(f - 0.5f);
	}

	public static int signum(double d) {
		return d > 0D ? 1 : d < 0D ? -1 : 0;
	}

	public static int signum(float f) {
		return f > 0f ? 1 : f < 0f ? -1 : 0;
	}

	public static int signum(int i) {
		return i > 0 ? 1 : i < 0 ? -1 : 0;
	}
}
