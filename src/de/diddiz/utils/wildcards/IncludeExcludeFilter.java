package de.diddiz.utils.wildcards;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Filter that returns true, if the object matches the include patterns, but doesn't match the exclude patterns.
 */
public class IncludeExcludeFilter<T> implements Predicate<T>
{
	protected final PatternSet include, exclude;
	protected final Function<T, String> toStringFunc;

	public IncludeExcludeFilter(PatternSet include, PatternSet exclude, Function<T, String> toStringFunc) {
		this.include = include;
		this.exclude = exclude;
		this.toStringFunc = toStringFunc;
	}

	@Override
	public boolean test(T t) {
		final String str = toStringFunc.apply(t);
		return include.matchesAny(str) && !exclude.matchesAny(str);
	}
}
