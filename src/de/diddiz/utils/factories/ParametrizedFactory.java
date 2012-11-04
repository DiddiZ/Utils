package de.diddiz.utils.factories;

public interface ParametrizedFactory<T, V>
{
	public T create(V v);
}
