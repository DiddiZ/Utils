package de.diddiz.utils.crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import de.diddiz.utils.Utils;

public class MessageDigestWithBuffer
{
	private final byte[] buffer;
	private final MessageDigest md;

	public MessageDigestWithBuffer(MessageDigest md) {
		this(md, 4096);
	}

	public MessageDigestWithBuffer(MessageDigest md, int bufferSize) {
		this.md = md;
		buffer = new byte[bufferSize];
	}

	public String digest(File file) throws IOException {
		return Utils.digest(file, buffer, md);
	}

	public String digest(InputStream is) throws IOException {
		return Utils.digest(is, buffer, md);
	}

	public static MessageDigestWithBuffer createSHA1() throws NoSuchAlgorithmException {
		return new MessageDigestWithBuffer(MessageDigest.getInstance("SHA-1"));
	}
}
