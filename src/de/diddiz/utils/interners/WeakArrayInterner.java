package de.diddiz.utils.interners;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;
import com.google.common.collect.Interner;

/**
 * A {@link Interner} backed by a {@link WeakHashMap} designed for arrays.
 * <p>
 * Performs deep equals and deep hashing. This is necessary as naturally arrays only equals by reference.
 */
public class WeakArrayInterner<T> implements Interner<T[]>
{
	private final Map<WeakArrayInterner.ArrayWrapper<T>, Reference<WeakArrayInterner.ArrayWrapper<T>>> map = new WeakHashMap<>();

	@Override
	public T[] intern(T[] t) {
		final WeakArrayInterner.ArrayWrapper<T> wrapper = new WeakArrayInterner.ArrayWrapper<>(t);
		final Reference<WeakArrayInterner.ArrayWrapper<T>> reference = map.get(wrapper);
		if (reference != null) {
			final T[] result = reference.get().array;
			if (result != null)
				return result;
		}
		map.put(wrapper, new WeakReference<>(wrapper));
		return t;
	}

	private static final class ArrayWrapper<T>
	{
		private final T[] array;

		private ArrayWrapper(T[] array) {
			this.array = array;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null || !(obj instanceof WeakArrayInterner.ArrayWrapper))
				return false;
			final Object otherValue = ((WeakArrayInterner.ArrayWrapper<?>)obj).array;
			if (otherValue == null || !(otherValue instanceof Object[]))
				return false;
			return Arrays.equals(array, (Object[])otherValue);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(array);
		}
	}
}