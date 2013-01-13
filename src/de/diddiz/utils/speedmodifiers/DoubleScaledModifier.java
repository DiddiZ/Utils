package de.diddiz.utils.speedmodifiers;

/**
 * A wrapper for a Modifier that divides the input by a fixed number before applying it to the Modifier and multiplies it afterwards with another fixed number to rescale it..
 */
public class DoubleScaledModifier extends ScaledModifier
{
	protected final float outputScale;

	public DoubleScaledModifier(SpeedModifier modifier, float inputScale, float outputScale) {
		super(modifier, inputScale);
		this.outputScale = outputScale;
	}

	@Override
	public float get(float input) {
		return modifier.modify(input / inputScale) * outputScale;
	}
}
