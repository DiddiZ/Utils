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
}
