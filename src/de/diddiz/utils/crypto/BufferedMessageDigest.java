package de.diddiz.utils.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import de.diddiz.utils.ProgressListener;
import de.diddiz.utils.Utils;

/**
 * @author Robin Kupper
 */
public class BufferedMessageDigest
{
	private final byte[] buffer;
	private final MessageDigest md;

	public BufferedMessageDigest(MessageDigest md) {
		this(md, 4096);
	}

	public BufferedMessageDigest(MessageDigest md, int bufferSize) {
		this.md = md;
		buffer = new byte[bufferSize];
	}

	public String digest(File file) throws IOException {
		return Utils.digest(file, buffer, md);
	}

	public String digest(File file, ProgressListener listener) throws IOException {
		try (InputStream is = new FileInputStream(file)) {
			return Utils.digest(is, buffer, md, listener);
		}
	}

	public String digest(InputStream is) throws IOException {
		return Utils.digest(is, buffer, md);
	}

	public String digest(InputStream is, ProgressListener listener) throws IOException {
		return Utils.digest(is, buffer, md, listener);
	}

	public static BufferedMessageDigest createSHA1() {
		return createSHA1(4096);
	}

	public static BufferedMessageDigest createSHA1(int bufferSize) {
		try {
			return new BufferedMessageDigest(MessageDigest.getInstance("SHA-1"), bufferSize);
		} catch (final NoSuchAlgorithmException ex) {
			throw new AssertionError(ex);
		}
	}
}
