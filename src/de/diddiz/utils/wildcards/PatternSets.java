package de.diddiz.utils.wildcards;

import java.util.Arrays;
import java.util.List;

/**
 * @author Robin Kupper
 */
public final class PatternSets
{
	public static PatternSet createPatternSet(List<WildcardPattern> patterns) {
		if (patterns.size() == 0)
			throw new IllegalArgumentException("No pattern supplied");
		if (patterns.size() == 1)
			return new SinglePatternSet(patterns.get(0));
		return new ArrayPatternSet(patterns.toArray(new WildcardPattern[patterns.size()]));
	}

	public static PatternSet createPatternSet(String... patterns) {
		final WildcardPattern[] compiledPatterns = new WildcardPattern[patterns.length];
		for (int i = 0; i < patterns.length; i++)
			compiledPatterns[i] = WildcardPatterns.compile(patterns[i]);
		return createPatternSet(compiledPatterns);
	}

	public static PatternSet createPatternSet(WildcardPattern... patterns) {
		if (patterns.length == 0)
			throw new IllegalArgumentException("No pattern supplied");
		if (patterns.length == 1)
			return new SinglePatternSet(patterns[0]);
		return new ArrayPatternSet(patterns);
	}

	private static class ArrayPatternSet extends PatternSet
	{
		private final WildcardPattern[] patterns;

		public ArrayPatternSet(WildcardPattern[] patterns) {
			this.patterns = patterns;
		}

		@Override
		public boolean matchesAll(String text) {
			for (final WildcardPattern pattern : patterns)
				if (!pattern.matches(text))
					return false;
			return true;
		}

		@Override
		public boolean matchesAny(String text) {
			for (final WildcardPattern pattern : patterns)
				if (pattern.matches(text))
					return true;
			return false;
		}

		@Override
		public String toString() {
			return Arrays.toString(patterns);
		}
	}

	private static class SinglePatternSet extends PatternSet
	{
		private final WildcardPattern pattern;

		public SinglePatternSet(WildcardPattern pattern) {
			this.pattern = pattern;
		}

		@Override
		public boolean matchesAll(String text) {
			return pattern.matches(text);
		}

		@Override
		public boolean matchesAny(String text) {
			return pattern.matches(text);
		}

		@Override
		public String toString() {
			return '[' + pattern.toString() + ']';
		}
	}
}
