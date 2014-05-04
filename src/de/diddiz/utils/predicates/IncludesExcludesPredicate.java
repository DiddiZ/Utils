package de.diddiz.utils.predicates;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;
import de.diddiz.utils.logging.Log;
import de.diddiz.utils.wildcards.PatternSet;

/**
 * A filter that matches filenames against sets of include and exclude patterns.
 * <p>
 * Supplied files are firstly stripped of the root path. E.g. if the root prefix is "<code>c:/programs/</code>" and the applied file is "<code>c:/programs/myfile.txt</code>","<code>myfile.txt</code>" will be used for matching.
 * <p>
 * Paths must match includes and mustn't match excludes in order to return true.
 */
public final class IncludesExcludesPredicate implements Predicate<File>
{
	private final PatternSet includes, excludes;
	private final String rootPrefix;

	/**
	 * No root prefix. All paths are matched as absolute.
	 *
	 * @see #IncludesExcludesPredicate(PatternSet, PatternSet, String)
	 */
	public IncludesExcludesPredicate(PatternSet includes, PatternSet excludes) {
		this(includes, excludes, null);
	}

	/**
	 * All patterns are required to be lowercase.
	 *
	 * @param includes Patterns that must be matched, <code>null</code> to include all.
	 * @param excludes Patterns that mustn't be matched, <code>null</code> to exclude nothing.
	 * @param rootPrefix Prefix than ill be stripped of all paths before matching
	 */
	public IncludesExcludesPredicate(PatternSet includes, PatternSet excludes, String rootPrefix) {
		this.includes = includes;
		this.excludes = excludes;
		this.rootPrefix = normalizePath(rootPrefix);
	}

	@Override
	public boolean test(File file) {
		try {
			String path = normalizePath(file);

			// Strip the root prefix
			if (rootPrefix != null && path.startsWith(rootPrefix))
				path = path.substring(rootPrefix.length());

			return (includes == null || includes.matchesAny(path)) &&
					(excludes == null || !excludes.matchesAny(path));
		} catch (final IOException ex) {
			Log.warning("Failed to normalize " + file + ": ", ex);
			return false;
		}
	}

	private static String normalizePath(File file) throws IOException {
		return normalizePath(file.getCanonicalPath());
	}

	private static String normalizePath(String path) {
		// We are case insensitive
		path = path.toLowerCase();

		// If path contains backslashes instead of forward ones, replace these
		if (path.indexOf('\\') != -1)
			path = path.replace('\\', '/');

		return path;
	}
}
