package de.diddiz.utils.crypto;

import static com.google.common.base.Charsets.UTF_8;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Robin Kupper
 */
public class Crypto
{
	/**
	 * Creates an AES decrypting {@code Reader}.
	 */
	@SuppressWarnings("resource")
	public static Reader decryptingReader(File file, SecretKeySpec secretKey) throws NoSuchPaddingException, InvalidKeyException, FileNotFoundException {
		try {
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new InputStreamReader(new CipherInputStream(new BufferedInputStream(new FileInputStream(file)), cipher), UTF_8);
		} catch (final NoSuchAlgorithmException ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * Creates an AES encrypting {@code Writer}.
	 */
	@SuppressWarnings("resource")
	public static Writer encryptingWriter(File file, SecretKeySpec secretKey) throws NoSuchPaddingException, InvalidKeyException, FileNotFoundException {
		try {
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return new OutputStreamWriter(new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(file)), cipher), UTF_8);
		} catch (final NoSuchAlgorithmException ex) {
			throw new AssertionError(ex);
		}
	}
}
