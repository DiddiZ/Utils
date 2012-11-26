package de.diddiz.utils;

public class IntMath
{
	public static int border(int val, int min, int max) {
		if (val < min)
			return min;
		if (val > max)
			return max;
		return val;
	}

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
