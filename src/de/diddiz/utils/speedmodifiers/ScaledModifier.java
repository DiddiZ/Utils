package de.diddiz.utils.speedmodifiers;

/**
 * A wrapper for a Modifier that divides the input by a fixed number before applying it to the Modifier.
 */
public class ScaledModifier
{
	protected final SpeedModifier modifier;
	protected final float inputScale;

	public ScaledModifier(SpeedModifier modifier, float inputScale) {
		this.modifier = modifier;
		this.inputScale = inputScale;
	}

	public float get(float input) {
		return modifier.modify(input / inputScale);
	}

	public boolean is(float input) {
		return get(input) > 0.5f;
	}
}
