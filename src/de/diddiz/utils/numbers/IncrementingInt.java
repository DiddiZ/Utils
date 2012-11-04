package de.diddiz.utils.numbers;

public class IncrementingInt extends IntNumber
{
	protected final int min, max, step;

	public IncrementingInt(int min, int max) {
		this(min, max, 1);
	}

	public IncrementingInt(int min, int max, int step) {
		this(min, max, step, min);
	}

	public IncrementingInt(int min, int max, int step, int initialValue) {
		super(initialValue);
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		this.step = step;
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
