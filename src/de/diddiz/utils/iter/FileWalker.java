package de.diddiz.utils.iter;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Creates an {@link java.util.Iterator Iterator} that iterates over all sub files of a folder recursively.
 * 
 * Opens directories as needed while iterating, not ahead of.
 */
public class FileWalker implements Iterable<File>
{
	private final File root;

	/**
	 * @param root May be a directory, a file, or point to nothing. Respectively, the {@code Iterator} will iterate over all sub files in all sub directories, exactly the specified file, or nothing.
	 */
	public FileWalker(File root) {
		this.root = root;
	}

	@Override
	public Iterator<File> iterator() {
		return new FileWalkerIterator(root);
	}

	private static class FileWalkerIterator extends ComputeNextIterator<File>
	{
		private final LinkedList<File> folders = new LinkedList<>(), files = new LinkedList<>();

		public FileWalkerIterator(File root) {
			add(root);
		}

		@Override
		protected File computeNext() {
			while (files.size() == 0 && folders.size() > 0) {
				final File[] subFiles = folders.poll().listFiles();
				if (subFiles != null && subFiles.length > 0) // listFiles may return null.
					for (final File file : subFiles)
						add(file);
			}
			if (files.size() > 0)
				return files.poll();
			return null;
		}

		private void add(File file) {
			if (file.isDirectory())
				folders.add(file);
			else if (file.isFile())
				files.add(file);
		}
	}
}