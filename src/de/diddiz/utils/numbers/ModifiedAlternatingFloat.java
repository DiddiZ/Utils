package de.diddiz.utils.numbers;

import de.diddiz.utils.speedmodifiers.SpeedModifier;

public class ModifiedAlternatingFloat extends AlternatingFloat
{
	protected final SpeedModifier modifier;
	protected final float range;

	public ModifiedAlternatingFloat(float min, float max, float step, float initialValue, SpeedModifier modifier) {
		super(min, max, step, initialValue);
		this.modifier = modifier;
		range = this.max - this.min;
	}

	public ModifiedAlternatingFloat(float min, float max, float step, SpeedModifier modifier) {
		this(min, max, step, min, modifier);
	}

	@Override
	public float get() {
		final float factor = modifier.modify((value - min) / range);
		return min + factor * range;
	}
}
