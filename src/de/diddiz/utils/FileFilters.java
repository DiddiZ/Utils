package de.diddiz.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author Robin Kupper
 */
public final class FileFilters
{
	/**
	 * Simple {@link FilenameFilter} that matches {@code Files} agains an extension
	 *
	 * @param extension Case insensitive.
	 */
	public static FilenameFilter endsWith(final String extension) {
		final String ext = extension.toLowerCase();
		return new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(ext);
			}
		};
	}
}
