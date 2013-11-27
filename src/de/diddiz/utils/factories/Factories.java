package de.diddiz.utils.factories;

import java.util.ArrayList;
import java.util.List;

public final class Factories
{
	/**
	 * Creates a {@code T} from an arbitrary number of factories using the same {@code V}
	 */
	@SafeVarargs
	public static <T, V> List<T> create(V v, ParametrizedFactory<T, V>... factories) {
		final List<T> list = new ArrayList<>(factories.length);
		for (int i = 0; i < factories.length; i++)
			list.add(factories[i].create(v));
		return list;
	}

	public static <T> Factory<T> fixedFactory(final T value) {
		return new Factory<T>() {
			@Override
			public T create() {
				return value;
			}
		};
	}
}
