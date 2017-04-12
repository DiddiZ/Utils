package de.diddiz.utils.numbers;

import java.util.Random;

/**
 * @author Robin Kupper
 */
public abstract class MutableNumber extends Number
{
	protected final static Random rnd = new Random();

	public abstract void next();
}
