package de.diddiz.utils;

/**
 * @author Robin Kupper
 */
public final class DoubleMath
{
	/**
	 * Computes and returns the largest (nearest to positive infinity) value.
	 *
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static double max(double... arr) {
		double max = arr[0];
		for (int i = 1; i < arr.length; i++)
			if (arr[i] > max)
				max = arr[i];
		return max;
	}

	/**
	 * Computes and returns the largest (nearest to positive infinity) value.
	 *
	 * @throws ArrayIndexOutOfBoundsException When array is empty.
	 */
	public static double max(double[][] arr) {
		double max = max(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			final double tmp = max(arr[i]);
			if (tmp > max)
				max = tmp;
		}
		return max;
	}
}
