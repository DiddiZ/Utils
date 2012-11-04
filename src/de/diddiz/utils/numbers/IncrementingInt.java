package de.diddiz.utils.numbers;

public class IncrementingInt extends IntNumber
{
	protected final int min, max, step, range;

	public IncrementingInt(int min, int max) {
		this(min, max, 1);
	}

	public IncrementingInt(int lower, int upper, int step) {
		this(lower, upper, step, lower + rnd.nextInt(upper - lower + 1));
	}

	public IncrementingInt(int min, int max, int step, int initialValue) {
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
