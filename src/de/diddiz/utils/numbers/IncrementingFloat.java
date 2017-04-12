package de.diddiz.utils.numbers;

/**
 * @author Robin Kupper
 */
public class IncrementingFloat extends FloatNumber
{
	protected final float min, max, step;

	public IncrementingFloat(float min, float max, float step) {
		this(min, max, step, min);
	}

	public IncrementingFloat(float min, float max, float step, float initialValue) {
		super(initialValue);
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		this.step = step;
	}

	public float getMax() {
		return max;
	}

	public float getMin() {
		return min;
	}

	public float getStep() {
		return step;
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
