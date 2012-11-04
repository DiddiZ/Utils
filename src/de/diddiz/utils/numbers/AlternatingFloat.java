package de.diddiz.utils.numbers;

public class AlternatingFloat extends IncrementingFloat
{
	protected float dir = 1;

	public AlternatingFloat(float min, float max, float step, float initialValue) {
		super(min, max, step, initialValue);
	}

	public AlternatingFloat(float min, float max, float step) {
		super(min, max, step);
	}

	public AlternatingFloat(float min, float max) {
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
