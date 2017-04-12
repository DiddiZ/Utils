package de.diddiz.utils;

/**
 * @author Robin Kupper
 */
public final class ReflectionUtils
{
	/**
	 * @param clazz Class to be tested
	 * @param inter Interface
	 * @return whether one of clazz or its superclasses implements inter
	 */
	public static boolean implementsInterface(Class<?> clazz, Class<?> inter) {
		while (clazz != null) {
			for (final Class<?> i : clazz.getInterfaces())
				if (i == inter)
					return true;
			clazz = clazz.getSuperclass();
		}
		return false;
	}
}
