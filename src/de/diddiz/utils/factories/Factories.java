package de.diddiz.utils.factories;

import java.util.concurrent.Callable;

public class Factories
{
	public static <T> Factory<T> fixedFactory(final T value) {
		return new Factory<T>() {
			@Override
			public T create() {
				return value;
			}
		};
	}

	public static <T> Callable<T> wrap(final Factory<T> factory) {
		return new Callable<T>() {
			@Override
			public T call() {
				return factory.create();
			}
		};
	}
}
