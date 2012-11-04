package de.diddiz.utils.numbers;

public class GentleAlternatingFloat extends AlternatingFloat
{
	public GentleAlternatingFloat(float min, float max, float step, float initialValue) {
		super(min, max, step, initialValue);
	}

	public GentleAlternatingFloat(float min, float max, float step) {
		super(min, max, step);
	}

	public GentleAlternatingFloat(float min, float max) {
		super(min, max);
	}

	@Override
	public float get() {
		final float factor = (value - min) / range;
		if (factor <= 0.5f)
			return factor * factor * 2 * range + min;
		return (1 - (factor - 1) * (factor - 1) * 2) * range + min;
	}
}
