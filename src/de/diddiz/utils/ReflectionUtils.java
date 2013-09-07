package de.diddiz.utils;

public final class ReflectionUtils
{
	/**
	 * @param clazz Class to be tested
	 * @param inter Interface
	 * @return If one of clazz or its superclasses implements interface inter
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
