package de.diddiz.utils.factories;

/**
 * @author Robin Kupper
 */
public interface ParametrizedFactory<T, V>
{
	public T create(V v);
}
