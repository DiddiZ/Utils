package de.diddiz.utils.numbers;

import de.diddiz.utils.FloatMath;

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
		value += FloatMath.fence(angle, -speed, speed);
	}
}
