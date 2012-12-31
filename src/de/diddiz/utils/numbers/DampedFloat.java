package de.diddiz.utils.numbers;

import static de.diddiz.utils.FloatMath.clamp;

public class DampedFloat extends FloatNumber
{
	protected final float speed;
	protected float target;

	public DampedFloat(float value, float speed) {
		super(value);
		this.speed = speed;
	}

	@Override
	public void next() {
		value += clamp(target - value, -speed, speed);
	}

	/**
	 * Equivalent to:
	 * 
	 * <pre>
	 * setTarget(target);
	 * next();
	 * </pre>
	 */
	public void next(float target1) {
		setTarget(target1);
		next();
	}

	public void setTarget(float target) {
		this.target = target;
	}
}
