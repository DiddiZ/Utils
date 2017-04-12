package de.diddiz.utils.iter;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import com.google.common.collect.AbstractIterator;

/**
 * Iterates over all sub-directories of a folder.
 */
public class DirectoryWalker implements Iterable<File>
{
	private final File[] roots;

	/**
	 * The {@code Iterator} will iterate over the directory itself and all sub-folders in all sub-directories.
	 * The {@code Iterator} only returns existing directories.
	 * 
	 * @param roots May be a directory or point to nothing.
	 */
	public DirectoryWalker(File... roots) {
		this.roots = roots;
	}

	@Override
	public Iterator<File> iterator() {
		return new DirectoryWalkerIterator(roots);
	}

	private static class DirectoryWalkerIterator extends AbstractIterator<File>
	{
		private final LinkedList<File> folders = new LinkedList<>();

		public DirectoryWalkerIterator(File... roots) {
			for (final File root : roots)
				if (root.isDirectory())
					folders.add(root);
		}

		@Override
		protected File computeNext() {
			if (folders.size() > 0) {
				final File folder = folders.poll();

				// Add all sub dirs
				final File[] subFiles = folder.listFiles();
				if (subFiles != null && subFiles.length > 0)
					for (final File file : subFiles)
						if (file.isDirectory())
							folders.add(file);

				return folder;
			}
			// We have no more folders
			return endOfData();
		}
	}
}