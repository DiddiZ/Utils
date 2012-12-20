package de.diddiz.utils.numbers;

import de.diddiz.utils.speedmodifiers.SpeedModifier;

public class ModifiedFloat extends FloatNumber
{
	protected final SpeedModifier modifier;
	protected final IncrementingFloat base;
	protected final float range;

	public ModifiedFloat(IncrementingFloat base, SpeedModifier modifier) {
		super(calcValue(modifier, base.value, base.min, base.max - base.min));
		this.base = base;
		this.modifier = modifier;
		range = base.max - base.min;
	}

	@Override
	public void next() {
		base.next();
		final float factor = modifier.modify((base.value - base.min) / range);
		value = base.min + factor * range;
	}

	private static float calcValue(SpeedModifier modifier, float value, float min, float range) {
		final float factor = modifier.modify((value - min) / range);
		return min + factor * range;
	}
}
