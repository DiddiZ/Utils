package de.diddiz.utils.iter;

import java.io.File;
import java.util.Iterator;
import de.diddiz.utils.wildcards.PatternSet;

/**
 * Matches all {@link File} against {@code includes} and {@code excludes} patterns.
 * 
 * Only returns {@code Files} that match at least one include and none exclude pattern.
 * 
 * File paths are converted to lower case, backslashes ({@code \}) are replaced with slashes ({@code /}), and the {@code rootPrefix} is removed (if present) before matching.
 */
public class FilteredFileWalker implements Iterable<File>
{
	private final FileWalker fileWalker;
	private final PatternSet includes, excludes;
	private final String rootPrefix;

	/**
	 * @param includes May be null to include all
	 * @param excludes May be null to exclude none
	 */
	public FilteredFileWalker(FileWalker fileWalker, PatternSet includes, PatternSet excludes) {
		this(fileWalker, includes, excludes, null);
	}

	/**
	 * @param includes May be null to include all
	 * @param excludes May be null to exclude none
	 * @param rootPrefix Root path to cut from files before matching.
	 */
	public FilteredFileWalker(FileWalker fileWalker, PatternSet includes, PatternSet excludes, String rootPrefix) {
		this.fileWalker = fileWalker;
		this.includes = includes;
		this.excludes = excludes;
		this.rootPrefix = rootPrefix.toLowerCase().replace('\\', '/');
	}

	@Override
	public Iterator<File> iterator() {
		return new FilteredFileWalkerIterator(fileWalker);
	}

	private class FilteredFileWalkerIterator extends ComputeNextIterator<File>
	{
		private final Iterator<File> itr;

		public FilteredFileWalkerIterator(FileWalker fileWalker) {
			itr = fileWalker.iterator();
			computeFirst();
		}

		@Override
		protected File computeNext() {
			while (itr.hasNext()) {
				final File file = itr.next();
				String path = file.getPath().toLowerCase();
				if (path.indexOf('\\') != -1)
					path = path.replace('\\', '/');
				if (path.startsWith(rootPrefix))
					path = path.substring(rootPrefix.length());

				if ((includes == null || includes.matchAny(path)) &&
						(excludes == null || !excludes.matchAny(path)))
					return file;
			}
			return null;
		}
	}
}
