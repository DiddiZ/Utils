package de.diddiz.utils;

import java.io.File;
import java.io.FilenameFilter;

public final class FileFilters
{
	public static FilenameFilter endsWith(final String extension) {
		return new FilenameFilter() {
			String ext = extension.toLowerCase();

			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(ext);
			}
		};
	}
}
