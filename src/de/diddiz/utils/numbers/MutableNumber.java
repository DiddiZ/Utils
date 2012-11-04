package de.diddiz.utils.numbers;

import java.util.Random;

public abstract class MutableNumber extends Number
{
	protected final static Random rnd = new Random();

	Integer i;

	public abstract void next();
}
