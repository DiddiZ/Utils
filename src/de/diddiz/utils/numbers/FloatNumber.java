package de.diddiz.utils.numbers;

public abstract class FloatNumber extends MutableNumber
{
	protected float value;

	public FloatNumber(float value) {
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

	public float get() {
		return value;
	}

	@Override
	public int intValue() {
		return (int)value;
	}

	@Override
	public long longValue() {
		return (long)value;
	}
}
