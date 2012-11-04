package de.diddiz.utils.numbers;

public class GentleStartStopAlternatingFloat extends AlternatingFloat
{
	protected static final float fourThirds = (float)(0.4 / 0.3);

	public GentleStartStopAlternatingFloat(float min, float max, float step, float initialValue) {
		super(min, max, step, initialValue);
	}

	public GentleStartStopAlternatingFloat(float min, float max, float step) {
		super(min, max, step);
	}

	public GentleStartStopAlternatingFloat(float min, float max) {
		super(min, max);
	}

	@Override
	public float get() {
		final float factor = (value - min) / range;

		if (factor <= 0.2f)
			return factor * factor * 2.5f * range + min;
		else if (factor < 0.8f)
			return (0.5f + (factor - 0.5f) * fourThirds) * range + min;
		else
			return (1 - (factor - 1) * (factor - 1) * 2.5f) * range + min;
	}
}
