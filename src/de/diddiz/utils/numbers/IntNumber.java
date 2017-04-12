package de.diddiz.utils.numbers;

/**
 * @author Robin Kupper
 */
public abstract class IntNumber extends MutableNumber
{
	protected int value;

	public IntNumber(int value) {
		this.value = value;
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value;
	}

	/**
	 * Returns the current int value.
	 */
	public int get() {
		return value;
	}

	/**
	 * Calls {@link #next()} and returns the new int value.
	 */
	public int getNext() {
		next();
		return value;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return value;
	}
}