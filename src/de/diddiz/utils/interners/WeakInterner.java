package de.diddiz.utils.interners;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

/**
 * A simple {@link Interner} backed by a {@link WeakHashMap}.
 * <p>
 * This is about 2-3 times faster than {@link Interners#newWeakInterner()} and consumes about the same memory.
 */
public class WeakInterner<T> implements Interner<T>
{
	private final Map<T, Reference<T>> map = new WeakHashMap<>();

	@Override
	public T intern(T t) {
		final Reference<T> reference = map.get(t);
		if (reference != null) {
			final T result = reference.get();
			if (result != null)
				return result;
		}
		map.put(t, new WeakReference<>(t));
		return t;
	}
}