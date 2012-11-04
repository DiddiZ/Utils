package de.diddiz.utils.numbers;

public class AlternatingInt extends IncrementingInt
{
	protected int dir = 1;

	public AlternatingInt(int min, int max, int step, int initialValue) {
		super(min, max, step, initialValue);
	}

	public AlternatingInt(int min, int max) {
		super(min, max);
	}

	@Override
	public void next() {
		value += step * dir;
		if (value >= max) {
			value = max;
			dir = -1;
		} else if (value <= min) {
			value = min;
			dir = 1;
		}
	}
}
