package de.diddiz.utils.numbers;

import static de.diddiz.utils.FloatMath.clamp;

/**
 * @author Robin Kupper
 */
public class DampedDegreeFloat extends DampedFloat
{
	public DampedDegreeFloat(float value, float speed) {
		super(value, speed);
	}

	@Override
	public void next() {
		float angle = ((target - value) % 360f + 360f) % 360f;
		if (angle > 180f)
			angle -= 360f;
		value += clamp(angle, -speed, speed);
	}
}
