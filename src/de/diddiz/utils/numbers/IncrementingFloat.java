package de.diddiz.utils.numbers;

public class IncrementingFloat extends FloatNumber
{
	protected final float min, max, step, range;

	public IncrementingFloat(float min, float max) {
		this(min, max, 1);
	}

	public IncrementingFloat(float lower, float upper, float step) {
		this(lower, upper, step, lower + rnd.nextFloat() * Math.abs(upper - lower));
	}

	public IncrementingFloat(float min, float max, float step, float initialValue) {
		super(initialValue);
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		this.step = step;
		range = max - min;
	}

	@Override
	public void next() {
		value += step;
		if (value > max)
			value = min;
		else if (value < min)
			value = max;
	}
}
