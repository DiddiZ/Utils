package de.diddiz.utils;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import java.awt.Desktop;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.crypto.Cipher;
import javax.swing.JFrame;
import de.diddiz.utils.UtilsClasses.BytesFormat;

public final class Utils
{
	public static final String NEWLINE = System.getProperty("line.separator"), TAB = "\t";
	public static final char QOUTE = '\"';
	public static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance(Locale.US), DECIMAL_FORMAT = NumberFormat.getNumberInstance(Locale.US);
	public static final Random RANDOM = new Random();

	/**
	 * @param len Desired length of the returned String
	 * @return A String consisting of random chars in {@code [a-zA-Z0-9]}
	 */
	public static String alphaNum(int len) {
		final String dict = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		final char[] alphaNum = new char[len];
		for (int i = 0; i < len; i++)
			alphaNum[i] = dict.charAt(RANDOM.nextInt(dict.length()));
		return new String(alphaNum);
	}

	/**
	 * Capitalizes the first letter of every word in the {@code String}, the rest is turned to lower case.
	 */
	public static String capitalize(CharSequence str) {
		final char[] chars = new char[str.length()];

		boolean capitalize = true;
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			if (c == ' ') {
				chars[i] = c;
				capitalize = true;
			} else if (capitalize == true) {
				chars[i] = Character.toUpperCase(c);
				capitalize = false;
			} else
				chars[i] = Character.toLowerCase(c);
		}
		return new String(chars);
	}

	/**
	 * Adds {@code Strings} to a {@code String array}.
	 * 
	 * @return New {@code String array} containing all {@code Strings}.
	 */
	public static String[] concat(String[] arr1, String... arr2) {
		final String[] narr = new String[arr1.length + arr2.length];
		System.arraycopy(arr1, 0, narr, 0, arr1.length);
		System.arraycopy(arr2, 0, narr, arr1.length, arr2.length);
		return narr;
	}

	/**
	 * @return true if {@code set} contains at lest one element of {@code values}.
	 */
	public static boolean containsAny(Set<?> set, Iterable<?> values) {
		for (final Object value : values)
			if (set.contains(value))
				return true;
		return false;
	}

	/**
	 * Uses {@code 4096} as buffer size.
	 * 
	 * @see #copy(InputStream, OutputStream, int)
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		copy(in, out, 4096);
	}

	/**
	 * Use this method when you want to perform a lot of copy actions, so you can recycle the buffer.
	 * 
	 * @param buffer Byte array that will be used as buffer.
	 */
	public static void copy(InputStream in, OutputStream out, byte[] buffer) throws IOException {
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
	}

	/**
	 * Calls the listener after each chunk. Preferably choose a large buffer to keep the overhead minimal.
	 * 
	 * @param buffer Byte array that will be used as buffer.
	 */
	public static void copy(InputStream in, OutputStream out, byte[] buffer, ProgressListener listener) throws IOException {
		long written = 0;
		int len;
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
			listener.onProgress(written += len);
		}
	}

	/**
	 * Creates a new byte array with desired length as buffer.
	 * 
	 * @see #copy(InputStream, OutputStream, byte[])
	 */
	public static void copy(InputStream in, OutputStream out, int bufferSize) throws IOException {
		copy(in, out, new byte[bufferSize]);
	}

	/**
	 * Uses {@code 4096} as buffer size.
	 * 
	 * @see #copy(InputStream, OutputStream, int)
	 */
	public static void copy(Reader in, Writer out) throws IOException {
		copy(in, out, 4096);
	}

	/**
	 * Use this method when you want to perform a lot of copy actions, so you can recycle the buffer.
	 * 
	 * @param buffer Char array that will be used as buffer.
	 */
	public static void copy(Reader in, Writer out, char[] buffer) throws IOException {
		int len;
		while ((len = in.read(buffer)) >= 0)
			out.write(buffer, 0, len);
	}

	/**
	 * Calls the listener after each chunk. Preferably choose a large buffer to keep the overhead minimal.
	 * 
	 * @param buffer Char array that will be used as buffer.
	 */
	public static void copy(Reader in, Writer out, char[] buffer, ProgressListener listener) throws IOException {
		long written = 0;
		int len;
		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
			listener.onProgress(written += len);
		}
	}

	/**
	 * Creates a new char array with desired length as buffer.
	 * 
	 * @see #copy(Reader, Writer, char[])
	 */
	public static void copy(Reader in, Writer out, int bufferSize) throws IOException {
		copy(in, out, new char[bufferSize]);
	}

	public static void copyFile(File in, File out) throws IOException {
		out.createNewFile();
		try (final FileInputStream is = new FileInputStream(in); final FileChannel inChannel = is.getChannel(); final FileOutputStream os = new FileOutputStream(out); final FileChannel outChannel = os.getChannel()) {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		}
		out.setLastModified(in.lastModified());
	}

	/**
	 * Counts how many time a {@code char} occurs in a {@code String}.
	 */
	public static int countOccurrences(CharSequence haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++)
			if (haystack.charAt(i) == needle)
				count++;
		return count;
	}

	public static String decryptPassword(String encrypted, Key key) throws GeneralSecurityException {
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Utils.fromHex(encrypted)));
	}

	/**
	 * Tries to delete every file. If it still exists after attempting to delete, it will be requested for {@link File#deleteOnExit()}.
	 */
	public static void deleteFiles(Iterable<File> files) {
		for (final File file : files) {
			if (file.exists())
				file.delete();
			if (file.exists())
				file.deleteOnExit();
		}
	}

	public static String digest(File file, byte[] buffer, MessageDigest md) throws IOException {
		try (InputStream is = new FileInputStream(file)) {
			return digest(is, buffer, md);
		}
	}

	public static String digest(InputStream is, byte[] buffer, MessageDigest md) throws IOException {
		int len;
		while ((len = is.read(buffer)) >= 0)
			md.update(buffer, 0, len);
		return toHex(md.digest());
	}

	public static String digest(InputStream is, byte[] buffer, MessageDigest md, ProgressListener listener) throws IOException {
		long position = 0;
		int len;
		while ((len = is.read(buffer)) >= 0) {
			md.update(buffer, 0, len);
			listener.onProgress(position += len);
		}
		return toHex(md.digest());
	}

	/**
	 * Downloads a file. Lacking parent directories will be created.
	 */
	public static void download(URL url, File file) throws IOException {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdir();
		try (InputStream in = url.openStream(); OutputStream out = new FileOutputStream(file)) {
			copy(in, out);
		}
	}

	public static void echo(Object... objs) {
		final StringBuilder builder = new StringBuilder(toString(objs[0]));
		for (int i = 1; i < objs.length; i++)
			builder.append('|' + toString(objs[i]));
		echo(builder.toString());
	}

	public static void echo(String msg) {
		System.out.println(msg);
	}

	public static String encryptPassword(String password, Key key) throws GeneralSecurityException {
		final Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return toHex(cipher.doFinal(password.getBytes()));
	}

	/**
	 * Tests if two {@code CharSequence} have the same length and equal chars.
	 * 
	 * @return true if both are equal.
	 */
	public static boolean equals(CharSequence a, CharSequence b) {
		if (a.length() != b.length())
			return false;
		final int len = a.length();
		for (int i = 0; i < len; i++)
			if (a.charAt(i) != b.charAt(i))
				return false;
		return true;
	}

	public static void errorMessageBox(String msg) {
		final JFrame jf = new JFrame("tmp");
		jf.setLocationRelativeTo(null);
		jf.setSize(0, 0);
		jf.setUndecorated(true);
		jf.setVisible(true);
		jf.setAlwaysOnTop(true);
		jf.setAlwaysOnTop(false);

		showMessageDialog(jf, msg, "Error", ERROR_MESSAGE);
		jf.dispose();
	}

	public static void errorMessageBox(String msg, Throwable ex) {
		errorMessageBox(msg + ":" + NEWLINE + NEWLINE + toString(ex));
	}

	public static String fillWithSpacesLeft(int i, int len) {
		return spaces(len - String.valueOf(i).length()) + i;
	}

	public static String fillWithSpacesLeft(long i, int len) {
		return spaces(len - String.valueOf(i).length()) + i;
	}

	public static String fillWithSpacesLeft(String str, int len) {
		return spaces(len - str.length()) + str;
	}

	public static String format(Throwable thrown, String nl, String t) {
		final StringBuilder sb = new StringBuilder();
		format(thrown, sb, nl, t);
		if (thrown.getCause() != null)
			formatCause(thrown.getCause(), sb, nl, t);
		return sb.toString();
	}

	public static String formatBytes(long bytes) {
		return BytesFormat.INSTANCE.formatBytes(bytes);
	}

	public static String formatTime(int seconds) {
		String str = "";
		if (seconds / 86400 > 0)
			str += seconds / 86400 + "d ";
		if (seconds / 3600 > 0)
			str += seconds / 3600 % 24 + "h ";
		if (seconds / 60 > 0)
			str += seconds / 60 % 60 + "m ";
		str += seconds % 60 + "s ";
		return str;
	}

	public static byte[] fromHex(String hex) {
		final int len = hex.length() / 2;
		final byte[] data = new byte[len];
		int pos = 0;
		for (int i = 0; i < len; i++)
			data[i] = (byte)((hexValue(hex.charAt(pos++)) << 4) + hexValue(hex.charAt(pos++)));
		return data;
	}

	public static String getFileExtension(String fileName) {
		return getFileExtension(fileName, true);
	}

	public static String getFileExtension(String fileName, boolean withDot) {
		final int idx = fileName.lastIndexOf('.');
		return idx >= 0 ? fileName.substring(withDot ? idx : idx + 1, fileName.length()) : "";
	}

	/**
	 * Returns the last element in the array.
	 */
	public static <T> T getLast(T[] arr) {
		return arr[arr.length - 1];
	}

	public static String getMacAddress() throws SocketException {
		for (final NetworkInterface ni : Collections.list(NetworkInterface.getNetworkInterfaces())) {
			final byte[] hardwareAddress = ni.getHardwareAddress();
			if (hardwareAddress != null)
				return Utils.toHex(hardwareAddress);
		}
		return null;
	}

	public static <T> T getRandom(List<T> list) {
		return list.get(RANDOM.nextInt(list.size()));
	}

	/**
	 * Returns if both {@link Collection Collections} contains the same elements, in the same quantities, regardless of order and collection type.
	 * <p>
	 * Empty collections and {@code null} are regarded as equal.
	 */
	public static <T> boolean haveSameElements(Collection<T> col1, Collection<T> col2) {
		if (col1 == col2)
			return true;

		// If either list is null, return whether the other is empty
		if (col1 == null)
			return col2.isEmpty();
		if (col2 == null)
			return col1.isEmpty();

		// If lengths are not equal, they can't possibly match
		if (col1.size() != col2.size())
			return false;

		// Helper class, so we don't have to do a whole lot of autoboxing
		class Count
		{
			// Initialize as 1, as we would increment it anyway
			public int count = 1;
		}

		final Map<T, Count> counts = new HashMap<>();

		// Count the items in list1
		for (final T item : col1) {
			final Count count = counts.get(item);
			if (count != null)
				count.count++;
			else
				// If the map doesn't contain the item, put a new count
				counts.put(item, new Count());
		}

		// Subtract the count of items in list2
		for (final T item : col2) {
			final Count count = counts.get(item);
			// If the map doesn't contain the item here, then this item wasn't in list1
			if (count == null)
				return false;
			count.count--;
		}

		// If any count is nonzero at this point, then the two lists don't match
		for (final Count count : counts.values())
			if (count.count != 0)
				return false;

		return true;
	}

	public static int hexValue(char c) {
		switch (c) {
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
			case 'A':
				return 10;
			case 'B':
				return 11;
			case 'C':
				return 12;
			case 'D':
				return 13;
			case 'E':
				return 14;
			case 'F':
				return 15;
			default:
				throw new IllegalArgumentException(c + " is no hex char");
		}
	}

	/**
	 * Uses {@link Object#equals(Object)} to find the {@code needle}
	 * 
	 * @return index of {@code needle} in {@code array} or {@code -1}.
	 */
	public static <T> int indexOf(T[] haystack, T needle) {
		for (int i = 0; i < haystack.length; i++)
			if (haystack[i] != null && haystack[i].equals(needle))
				return i;
		return -1;
	}

	/**
	 * Ignores character case. For the case sensitive method see {@link #indexOf(Object[], Object)}
	 * 
	 * @return index of {@code needle} in {@code array} or {@code -1}.
	 */
	public static int indexOfIgnoreCase(String[] haystack, String needle) {
		needle = needle.toLowerCase();
		for (int i = 0; i < haystack.length; i++)
			if (haystack[i] != null && haystack[i].toLowerCase().equals(needle))
				return i;
		return -1;
	}

	public static boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (final NumberFormatException ex) {}
		return false;
	}

	public static boolean isFloat(String str) {
		try {
			Float.parseFloat(str);
			return true;
		} catch (final NumberFormatException ex) {}
		return false;
	}

	public static boolean isInt(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (final NumberFormatException ex) {}
		return false;
	}

	/**
	 * @param strings Strings to join, must not contain {@code nulls}.
	 * @param delimiter
	 * @return Concatenated {@code String} or an empty {@code String} if {@code strings} is {@code null} or empty.
	 */
	public static String join(Collection<String> strings, char delimiter) {
		if (strings == null || strings.size() == 0)
			return "";

		final StringBuilder sb = new StringBuilder(length(strings) + strings.size() - 1);
		final Iterator<String> itr = strings.iterator();
		sb.append(itr.next()); // We can do this safely as the collection has at least one element
		while (itr.hasNext())
			sb.append(delimiter).append(itr.next());
		return sb.toString();
	}

	/**
	 * @param strings Strings to join, must not contain {@code nulls}.
	 * @param delimiter
	 * @return Concatenated {@code String} or an empty {@code String} if {@code strings} is {@code null} or empty.
	 */
	public static String join(Collection<String> strings, CharSequence delimiter) {
		if (strings == null || strings.size() == 0)
			return "";

		final StringBuilder sb = new StringBuilder(length(strings) + (strings.size() - 1) * delimiter.length());
		final Iterator<String> itr = strings.iterator();
		sb.append(itr.next()); // We can do this safely as the collection has at least one element
		while (itr.hasNext())
			sb.append(delimiter).append(itr.next());
		return sb.toString();
	}

	/**
	 * Joins {@code Strings} without a delimiter.
	 * 
	 * @param arr Strings to join
	 * @return Concatenated {@code String} or an empty {@code String} if arr is {@code null} or empty.
	 */
	public static String join(String... arr) {
		if (arr == null || arr.length == 0)
			return "";
		final StringBuilder sb = new StringBuilder(length(arr));
		for (int i = 0; i < arr.length; i++)
			sb.append(arr[i]);
		return sb.toString();
	}

	/**
	 * @param arr Strings to join
	 * @param delimiter
	 * @return Concatenated {@code String} or an empty {@code String} if arr is {@code null} or empty.
	 */
	public static String join(String[] arr, char delimiter) {
		if (arr == null || arr.length == 0)
			return "";

		final StringBuilder sb = new StringBuilder(length(arr) + arr.length - 1);
		sb.append(arr[0]);
		for (int i = 1; i < arr.length; i++)
			sb.append(delimiter).append(arr[i]);
		return sb.toString();
	}

	/**
	 * In case you delimiter is just one char long, consider using {@link #join(String[], char)} for a minor speed boost.
	 */
	public static String join(String[] arr, CharSequence delimiter) {
		return join(arr, 0, delimiter);
	}

	public static String join(String[] arr, int offset, CharSequence delimiter) {
		if (arr == null || arr.length == 0 || arr.length < offset)
			return "";
		final StringBuilder sb = new StringBuilder(length(arr) + (arr.length - 1) * delimiter.length());
		sb.append(arr[offset]);
		for (int i = offset + 1; i < arr.length; i++)
			sb.append(delimiter).append(arr[i]);
		return sb.toString();
	}

	/**
	 * Calls {@link #slice(String[], int, int)} with {@code arr}, {@code 0}, {@code length}
	 * 
	 * @param length Strings to copy. May be nagative, position is then assumed to be indexed from right. i.e {@code -2} uses the position of the second last String ({@code arr.length - 2}).
	 */
	public static String[] left(String[] arr, int length) {
		if (length < 0)
			length += arr.length;
		return slice(arr, 0, length);
	}

	/**
	 * @param strings Must not be {@code null} or contain nulls.
	 * @return Length of all {@code Strings} summed up.
	 */
	public static int length(Iterable<String> strings) {
		int len = 0;
		for (final String s : strings)
			len += s.length();
		return len;
	}

	/**
	 * @param arr Must not be {@code null} or contain nulls.
	 * @return Length of all {@code Strings} summed up.
	 */
	public static int length(String[] arr) {
		int len = 0;
		for (final String s : arr)
			len += s.length();
		return len;
	}

	public static String listing(List<String> entries, String delimiter, String finalDelimiter) {
		final int len = entries.size();
		if (len == 0)
			return "";
		if (len == 1)
			return entries.get(0);
		final StringBuilder sb = new StringBuilder(length(entries) + delimiter.length() * (len - 2) + finalDelimiter.length());
		sb.append(entries.get(0));
		for (int i = 1; i < len - 1; i++)
			sb.append(delimiter).append(entries.get(1));
		sb.append(finalDelimiter).append(entries.get(len - 1));
		return sb.toString();
	}

	public static String listing(String[] entries, String delimiter, String finalDelimiter) {
		final int len = entries.length;
		if (len == 0)
			return "";
		if (len == 1)
			return entries[0];
		final StringBuilder sb = new StringBuilder(length(entries) + delimiter.length() * (len - 2) + finalDelimiter.length());
		sb.append(entries[0]);
		for (int i = 1; i < len - 1; i++)
			sb.append(delimiter).append(entries[i]);
		sb.append(finalDelimiter).append(entries[len - 1]);
		return sb.toString();
	}

	public static Map<String, Object> map(String[] keys, Object... values) {
		final Map<String, Object> map = new HashMap<>();
		final int len = Math.min(keys.length, values.length);
		for (int i = 0; i < len; i++)
			map.put(keys[i], values[i]);
		return map;
	}

	public static int maxStringLength(int... ints) {
		int max = 0;
		for (final int i : ints) {
			final int len = String.valueOf(i).length();
			if (len > max)
				max = len;
		}
		return max;
	}

	public static int maxStringLength(long... ints) {
		int max = 0;
		for (final long l : ints) {
			final int len = String.valueOf(l).length();
			if (len > max)
				max = len;
		}
		return max;
	}

	public static int maxStringLength(String... strs) {
		int max = 0;
		for (final String str : strs) {
			final int len = str.length();
			if (len > max)
				max = len;
		}
		return max;
	}

	public static void openBrowser(URI uri) throws IOException {
		if (Desktop.isDesktopSupported()) {
			final Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE))
				desktop.browse(uri);
		}
	}

	public static String read(File file) throws IOException {
		try (Reader reader = new FileReader(file)) {
			return read(reader);
		}
	}

	/**
	 * Reads an {@link java.io.InputStream InputStream} into a {@link java.io.StringWriter StringWriter} and returns its content. The {@code InputStream} {@code is} doesn't get closed.
	 */
	public static String read(InputStream is) throws IOException {
		try (final Reader reader = new InputStreamReader(is)) {
			return read(reader);
		}
	}

	/**
	 * Reads the content of a {@link Reader} into a {@code String}.
	 */
	public static String read(Reader reader) throws IOException {
		final StringBuilder content = new StringBuilder();
		final char[] buffer = new char[1024];
		int len;
		while ((len = reader.read(buffer)) >= 0)
			content.append(buffer, 0, len);
		return content.toString();
	}

	/**
	 * Reads the target of an {@link URL} into a {@code String}.
	 */
	public static String read(URL url) throws IOException {
		try (Reader reader = new InputStreamReader(url.openStream())) {
			return read(reader);
		}
	}

	/**
	 * Reads a number of bytes from an {@link InputStream}.
	 * 
	 * Ensures that the returned array is completely filled.
	 * 
	 * The {@code InputStream} is NOT closed!.
	 * 
	 * @throws EOFException If reached end of stream.
	 */
	public static byte[] readBytes(InputStream is, int bytes) throws IOException {
		final byte[] buffer = new byte[bytes];
		if (is.read(buffer) != bytes)
			throw new EOFException();
		return buffer;
	}

	/**
	 * Repeats a char
	 */
	public static String repeat(char c, int times) {
		final char[] chars = new char[times];
		Arrays.fill(chars, c);
		return new String(chars);
	}

	/**
	 * Repeats a String. Repeat(char, int) is much faster.
	 */
	public static String repeat(CharSequence str, int times) {
		final StringBuilder sb = new StringBuilder(str.length() * times);
		for (int i = 0; i < times; i++)
			sb.append(str);
		return sb.toString();
	}

	public static void restart(Class<?> mainClass) throws IOException {
		restart(mainClass, null);
	}

	public static void restart(Class<?> mainClass, String args) throws IOException {
		final StringBuilder cmd = new StringBuilder();
		final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		cmd.append('"' + System.getProperty("java.home") + File.separator + "bin" + File.separator + "java\" ");
		for (final String jvmArg : runtimeMXBean.getInputArguments())
			cmd.append(jvmArg + ' ');
		cmd.append("-cp ").append('"' + runtimeMXBean.getClassPath() + '"').append(' ');
		cmd.append(mainClass.getName()).append(' ');
		if (args != null)
			cmd.append(args);
		// showMessageDialog(null, new JTextField(cmd.toString()));
		Runtime.getRuntime().exec(cmd.toString());
		System.exit(0);
	}

	/**
	 * Reverses the order of a {@code List}.
	 * 
	 * Actually just calls {@link Collections#reverse(List)}, but returns the reversed list, so it makes this method chainable.
	 * 
	 * @return the reversed {@code List}.
	 */
	public static <T> List<T> reverse(List<T> list) {
		Collections.reverse(list);
		return list;
	}

	/**
	 * Creates a {@code String} with roman numerals from an {@code int}.
	 * 
	 * @param number Must be in the angel from 1 to 3999
	 */
	public static String romanNumerals(int number) {
		if (number < 1 || number > 3999)
			throw new IllegalArgumentException("Only values from 1 to 3999 are supported");
		final char[] ones = {'I', 'X', 'C', 'M'}, fives = {'V', 'L', 'D'};

		final StringBuilder sb = new StringBuilder();
		int e = 0;
		while (number > 0) {
			switch (number % 10) {
				case 0:// Nothing
					break;
				case 1: // I
					sb.append(ones[e]);
					break;
				case 2:// II
					sb.append(ones[e]).append(ones[e]);
					break;
				case 3:// III
					sb.append(ones[e]).append(ones[e]).append(ones[e]);
					break;
				case 4:// IV, reversed to VI
					sb.append(fives[e]).append(ones[e]);
					break;
				case 5:// V
					sb.append(fives[e]);
					break;
				case 6:// VI reversed to IV
					sb.append(ones[e]).append(fives[e]);
					break;
				case 7:// VII reversed to IIV
					sb.append(ones[e]).append(ones[e]).append(fives[e]);
					break;
				case 8:// VIII reversed to IIIV
					sb.append(ones[e]).append(ones[e]).append(ones[e]).append(fives[e]);
					break;
				case 9:// IX reversed to XI
					sb.append(ones[e + 1]).append(ones[e]);
					break;
			}
			number /= 10;
			e++;
		}
		return sb.reverse().toString();
	}

	/**
	 * Slices a part out of an array.
	 * 
	 * @param arr Array to be sliced.
	 * @param beginIndex must be equal or greater {@code 0}.
	 * @param endIndex must be equal or greater {@code beginIndex} and not greater than {@code arr.length}.
	 * 
	 * @return a new array, not a view. The length is equal to {@code endIndex - beginIndex}.
	 * @throws ArrayIndexOutOfBoundsException if prementioned index requirements aren't met.
	 */
	public static String[] slice(String[] arr, int beginIndex, int endIndex) {
		if (beginIndex < 0 || endIndex > arr.length || endIndex < beginIndex)
			throw new ArrayIndexOutOfBoundsException("Array (length: " + arr.length + ") doesn't contains both " + beginIndex + " and " + endIndex);
		final int len = endIndex - beginIndex;
		final String[] narr = new String[len];
		System.arraycopy(arr, beginIndex, narr, 0, len);
		return narr;
	}

	public static <T extends Comparable<T>> List<T> sort(Collection<T> col) {
		final List<T> list = new ArrayList<>(col);
		Collections.sort(list);
		return list;
	}

	public static <T> List<T> sort(Collection<T> col, Comparator<T> comparator) {
		final List<T> list = new ArrayList<>(col);
		Collections.sort(list, comparator);
		return list;
	}

	public static <T extends Comparable<T>> List<T> sort(List<T> list) {
		Collections.sort(list);
		return list;
	}

	public static <T> List<T> sort(List<T> list, Comparator<T> comparator) {
		Collections.sort(list, comparator);
		return list;
	}

	/**
	 * Repeats space chars
	 */
	public static String spaces(int times) {
		return repeat(' ', times);
	}

	/**
	 * Fully compatible with {@link String#split(String)}.
	 * 
	 * This has about the same performance as {@code String.split(String)} for long {@code Strings} but 25% lesser memory consumption. Has about twice computation speed for short {@code Strings}.
	 */
	public static String[] split(String str, char c) {
		if (str.length() == 0)
			return new String[]{""};

		boolean trailing = true; // Are we still finding trailing matches
		int matches = 0;
		for (int i = str.length() - 1; i >= 0; i--)
			if (str.charAt(i) == c) {
				if (!trailing)
					matches++;
			} else
				trailing = false;
		if (trailing) // Every char matched, after removing trailing empty strings is nothing left
			return new String[]{};
		if (matches == 0) // No match, return the original string
			return new String[]{str};

		final int splits = matches + 1;

		final String[] split = new String[splits];
		int offset = 0;
		for (int i = 0; i < split.length - 1; i++) {
			final int idx = str.indexOf(c, offset);
			split[i] = str.substring(offset, idx);
			offset = idx + 1;
		}
		final int idx = str.indexOf(c, offset);
		split[split.length - 1] = idx != -1 ? str.substring(offset, idx) : str.substring(offset);
		return split;
	}

	/**
	 * Appends {@code tail} to a {@code String} if it doen't already ends with it.
	 * 
	 * @return Either the original {@code String} or a concatenation of {@code str} and {@code tail}.
	 */
	public static String tail(String str, char trail) {
		return str.charAt(str.length() - 1) == trail ? str : str + trail;
	}

	/**
	 * Appends {@code tail} to a {@code String} if it doen't already ends with it.
	 * 
	 * @return Either the original {@code String} or a concatenation of {@code str} and {@code tail}.
	 */
	public static String tail(String str, String trail) {
		return str.endsWith(trail) ? str : str + trail;
	}

	/**
	 * Appends a tailing slash to a path if not present.
	 * 
	 * @return Either the original {@code String} or a concatenation of {@code path} and {@code tail}.
	 */
	public static String tailingSlash(String path) {
		final char lastChar = path.charAt(path.length() - 1);
		return lastChar == '/' || lastChar == '\\' ? path : path + '/';
	}

	public static String toBinary(byte b) {
		final char[] binary = new char[8];
		for (int i = 0; i < 8; i++)
			binary[i] = (b & 1 << 7 - i) > 0 ? '1' : '0';
		return new String(binary);
	}

	/**
	 * Uses {@code false} as default value.
	 * 
	 * @see #toBoolean(Object, boolean)
	 */
	public static boolean toBoolean(Object obj) {
		return toBoolean(obj, false);
	}

	/**
	 * Tries to get a {@code boolean} from {@code obj}.
	 * 
	 * <p>
	 * If {@code obj} is a {@code Boolean} it returns its value.
	 * </p>
	 * 
	 * <p>
	 * If {@code obj} is a {@code String} it returns whether it equals {@code "true"}.
	 * </p>
	 * 
	 * <p>
	 * Otherwise the default value is returned.
	 * </p>
	 */
	public static boolean toBoolean(Object obj, boolean def) {
		if (obj instanceof Boolean)
			return (Boolean)obj;
		if (obj instanceof String)
			return ((String)obj).equalsIgnoreCase("true");
		return def;
	}

	/**
	 * Uses {@code 0D} as default value.
	 * 
	 * @see #toDouble(Object, double)
	 */
	public static double toDouble(Object obj) {
		return toDouble(obj, 0D);
	}

	/**
	 * Tries to get a {@code double} from {@code obj}.
	 * 
	 * <p>
	 * If {@code obj} is a {@code Number} it returns its {@link java.lang.Number#doubleValue() doubleValue()}.
	 * </p>
	 * 
	 * <p>
	 * If {@code obj} is a {@code String} it tries to return {@link java.lang.Double#parseDouble(String) Double.parseDouble(obj)}.
	 * </p>
	 * 
	 * <p>
	 * Otherwise the default value is returned.
	 * </p>
	 */
	public static double toDouble(Object obj, double def) {
		if (obj instanceof Number)
			return ((Number)obj).doubleValue();
		if (obj instanceof String)
			try {
				return Double.parseDouble((String)obj);
			} catch (final NumberFormatException ex) {}
		return def;
	}

	/**
	 * Uses {@code 0f} as default value.
	 * 
	 * @see #toFloat(Object, float)
	 */
	public static float toFloat(Object obj) {
		return toFloat(obj, 0f);
	}

	/**
	 * Tries to get a {@code float} from {@code obj}.
	 * 
	 * <p>
	 * If {@code obj} is a {@code Number} it returns its {@link java.lang.Number#floatValue() floatValue()}.
	 * </p>
	 * 
	 * <p>
	 * If {@code obj} is a {@code String} it tries to return {@link java.lang.Float#parseFloat(String) Float.parseFloat(obj)}.
	 * </p>
	 * 
	 * <p>
	 * Otherwise the default value is returned.
	 * </p>
	 */
	public static float toFloat(Object obj, float def) {
		if (obj instanceof Number)
			return ((Number)obj).floatValue();
		if (obj instanceof String)
			try {
				return Float.parseFloat((String)obj);
			} catch (final NumberFormatException ex) {}
		return def;
	}

	public static String toHex(byte[] data) {
		final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		final char[] hex = new char[data.length * 2];
		int pos = 0;
		for (final byte b : data) {
			hex[pos++] = chars[(b & 0xF0) >> 4];
			hex[pos++] = chars[b & 0x0F];
		}
		return new String(hex);
	}

	/**
	 * Uses {@code 0} as default value.
	 * 
	 * @see #toInt(Object, int)
	 */
	public static int toInt(Object obj) {
		return toInt(obj, 0);
	}

	/**
	 * Tries to get a {@code int} from {@code obj}.
	 * 
	 * <p>
	 * If {@code obj} is a {@code Number} it returns its {@link java.lang.Number#intValue() intValue()}.
	 * </p>
	 * 
	 * <p>
	 * If {@code obj} is a {@code String} it tries to return {@link java.lang.Integer#parseInt(String) Integer.parseInt(obj)}.
	 * </p>
	 * 
	 * <p>
	 * Otherwise the default value is returned.
	 * </p>
	 */
	public static int toInt(Object obj, int def) {
		if (obj instanceof Number)
			return ((Number)obj).intValue();
		if (obj instanceof String)
			try {
				return Integer.parseInt((String)obj);
			} catch (final NumberFormatException ex) {}
		return def;
	}

	/**
	 * Uses {@code 0L} as default value.
	 * 
	 * @see #toLong(Object, long)
	 */
	public static long toLong(Object obj) {
		return toLong(obj, 0L);
	}

	/**
	 * Tries to get a {@code int} from {@code obj}.
	 * 
	 * <p>
	 * If {@code obj} is a {@code Number} it returns its {@link java.lang.Number#longValue() longValue()}.
	 * </p>
	 * 
	 * <p>
	 * If {@code obj} is a {@code String} it tries to return {@link java.lang.Long#parseLong(String) Long.parseLong(obj)}.
	 * </p>
	 * 
	 * <p>
	 * Otherwise the default value is returned.
	 * </p>
	 */
	public static long toLong(Object obj, long def) {
		if (obj instanceof Number)
			return ((Number)obj).longValue();
		if (obj instanceof String)
			try {
				return Long.parseLong((String)obj);
			} catch (final NumberFormatException ex) {}
		return def;
	}

	public static String toString(Object obj) {
		if (obj == null)
			return "null";
		if (obj instanceof String)
			return (String)obj;
		if (obj instanceof String[])
			return listing((String[])obj, ", ", ", ");
		if (obj instanceof Throwable)
			return toString((Throwable)obj);
		if (obj instanceof byte[])
			return toHex((byte[])obj);
		if (obj instanceof short[])
			return Arrays.toString((short[])obj);
		if (obj instanceof int[])
			return Arrays.toString((int[])obj);
		if (obj instanceof long[])
			return Arrays.toString((long[])obj);
		if (obj instanceof boolean[])
			return Arrays.toString((boolean[])obj);
		if (obj instanceof float[])
			return Arrays.toString((float[])obj);
		if (obj instanceof double[])
			return Arrays.toString((double[])obj);
		if (obj instanceof char[])
			return Arrays.toString((char[])obj);
		if (obj instanceof Object[])
			return Arrays.toString((Object[])obj);
		return obj.toString();
	}

	public static String toString(Throwable thrown) {
		return format(thrown, NEWLINE, TAB);
	}

	/**
	 * Calculates the next power of two.
	 */
	public static int twoFold(int number) {
		int ret = 2;
		while (ret < number)
			ret *= 2;
		return ret;
	}

	public static String uppercaseFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static void write(File file, String content) throws IOException {
		try (Writer writer = new FileWriter(file);) {
			writer.write(content);
		}
	}

	/**
	 * Writes a {@code String} to a file using a specific encoding
	 */
	public static void write(File file, String content, Charset encoding) throws IOException {
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), encoding)) {
			writer.write(content);
		}
	}

	public static void write(String file, String content) throws IOException {
		try (Writer writer = new FileWriter(file);) {
			writer.write(content);
		}
	}

	private static StringBuilder format(Throwable thrown, StringBuilder sb, String nl, String t) {
		sb.append(thrown.getClass().getName() + ": " + thrown.getLocalizedMessage() + nl);
		for (final StackTraceElement traceElement : thrown.getStackTrace())
			sb.append(t + "at " + traceElement + nl);
		return sb;
	}

	private static StringBuilder formatCause(Throwable cause, StringBuilder sb, String nl, String t) {
		sb.append("Caused by:" + nl);
		format(cause, sb, nl, t);
		if (cause.getCause() != null)
			formatCause(cause.getCause(), sb, nl, t);
		return sb;
	}
}
