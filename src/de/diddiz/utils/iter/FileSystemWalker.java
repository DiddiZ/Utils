package de.diddiz.utils.iter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.google.common.collect.AbstractIterator;

/**
 * Creates an {@link java.util.Iterator Iterator} that iterates over all sub paths of a set of paths recursively.
 * Opens directories as needed while iterating, not ahead of.
 */
public class FileSystemWalker implements Iterable<Path>
{
	private final Path[] roots;

	/**
	 * Uses the root directories of the {@link FileSystem}
	 */
	public FileSystemWalker(FileSystem fs) {
		this(getRoots(fs));
	}

	/**
	 * @param roots May be a directory, a file, or point to nothing. Respectively, the {@code Iterator} will iterate over all sub files in all sub directories, exactly the specified file, or nothing.
	 */
	public FileSystemWalker(Path... roots) {
		this.roots = roots;
	}

	@Override
	public Iterator<Path> iterator() {
		return new FileWalkerIterator(roots);
	}

	private static Path[] getRoots(FileSystem fs) {
		final List<Path> paths = new ArrayList<>(1);
		for (final Path path : fs.getRootDirectories())
			paths.add(path);
		return paths.toArray(new Path[paths.size()]);
	}

	private static class FileWalkerIterator extends AbstractIterator<Path>
	{
		private final LinkedList<Path> folders = new LinkedList<>(), files = new LinkedList<>();

		public FileWalkerIterator(Path[] roots) {
			for (final Path path : roots)
				add(path);
		}

		@Override
		protected Path computeNext() {
			while (files.size() == 0 && folders.size() > 0)
				try (DirectoryStream<Path> ds = Files.newDirectoryStream(folders.poll())) {
					for (final Path path : ds)
						add(path);
				} catch (final IOException ex) {
					throw new RuntimeException("Failed to read filesystem", ex);
				}
			if (files.size() > 0)
				return files.poll();
			return endOfData();
		}

		private void add(Path path) {
			if (Files.isDirectory(path))
				folders.add(path);
			else if (Files.exists(path))
				files.add(path);
		}
	}
}