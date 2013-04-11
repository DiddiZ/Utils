package de.diddiz.utils.iter;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

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

	private static class FileWalkerIterator implements Iterator<File>
	{
		private final LinkedList<File> folders = new LinkedList<>(), files = new LinkedList<>();
		private File next;

		public FileWalkerIterator(File root) {
			add(root);
			next = computeNext();
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public File next() {
			if (!hasNext())
				throw new NoSuchElementException();

			final File ret = next;
			next = computeNext();
			return ret;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		private void add(File file) {
			if (file.isDirectory())
				folders.add(file);
			else if (file.isFile())
				files.add(file);
		}

		private File computeNext() {
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
	}
}