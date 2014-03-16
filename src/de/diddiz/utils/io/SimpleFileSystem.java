package de.diddiz.utils.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import com.google.common.collect.ImmutableMap;

/**
 * Wrapper for a {@link FileSystem} to simplify common actions.
 */
public class SimpleFileSystem implements Closeable
{
	private final FileSystem fileSystem;

	public SimpleFileSystem(FileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	@Override
	public void close() throws IOException {
		fileSystem.close();
	}

	public boolean exists(String key) {
		final Path path = fileSystem.getPath(key);

		return Files.exists(path);
	}

	public Path getPath(String key) {
		return fileSystem.getPath(key);
	}

	public InputStream openInputStream(String key) throws IOException {
		return Files.newInputStream(getPath(key));
	}

	public OutputStream openOutputStream(String key) throws IOException {
		final Path path = fileSystem.getPath(key);

		mkParentDirs(path);
		return Files.newOutputStream(path, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public Reader openReader(String key, Charset charset) throws IOException {
		return Files.newBufferedReader(getPath(key), charset);
	}

	public Writer openWriter(String key, Charset charset) throws IOException {
		final Path path = fileSystem.getPath(key);

		mkParentDirs(path);
		return Files.newBufferedWriter(path, charset);
	}

	public void write(String key, InputStream is) throws IOException {
		final Path path = fileSystem.getPath(key);

		mkParentDirs(path);
		Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * Creates a file system inside a zip file.
	 */
	public static SimpleFileSystem createZipFileSystem(File file) throws IOException, URISyntaxException {
		if (!file.getParentFile().exists()) // Create parent folders
			file.getParentFile().mkdirs();

		return new SimpleFileSystem(FileSystems.newFileSystem(new URI("jar:" + file.toURI()), ImmutableMap.of("create", "true")));
	}

	private static void mkParentDirs(Path path) throws IOException {
		final Path parent = path.getParent();
		if (parent != null && Files.notExists(parent))
			Files.createDirectories(parent);
	}
}
