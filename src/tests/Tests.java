package tests;

import static de.diddiz.utils.Utils.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Predicate;
import org.junit.Test;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import com.google.common.io.Files;
import de.diddiz.utils.FloatMath;
import de.diddiz.utils.IntMath;
import de.diddiz.utils.TimeSpecParser;
import de.diddiz.utils.Utils;
import de.diddiz.utils.interners.WeakArrayInterner;
import de.diddiz.utils.io.SimpleFileSystem;
import de.diddiz.utils.math.NotANumberException;
import de.diddiz.utils.modifiers.Modifiers;
import de.diddiz.utils.numbers.AlternatingFloat;
import de.diddiz.utils.numbers.FloatNumber;
import de.diddiz.utils.numbers.ModifiedFloat;
import de.diddiz.utils.predicates.IncludesExcludesPredicate;
import de.diddiz.utils.serialization.DataNode;
import de.diddiz.utils.serialization.SerializedDataException;
import de.diddiz.utils.wildcards.PatternSet;
import de.diddiz.utils.wildcards.PatternSets;
import de.diddiz.utils.wildcards.WildcardPattern;
import de.diddiz.utils.wildcards.WildcardPatterns;

@SuppressWarnings("static-method")
public class Tests
{
	@Test
	public void testCapitalize() {
		assertEquals("Hallo Welt", Utils.capitalize("hallo welt"));
		assertEquals("Hallo Welt", Utils.capitalize("Hallo Welt"));
		assertEquals("A B C", Utils.capitalize("a b c"));
		assertEquals("Leer  Zeichen", Utils.capitalize("leer  zeichen"));
	}

	@Test
	public void testDataNode() throws SerializedDataException {
		final DataNode node = new DataNode('.', new LinkedHashMap<String, Object>());

		// Test basic set/get
		node.set("testInt", 5);
		assertEquals(5, node.get("testInt"));

		// Test deep set/get
		node.set("deep.testInt", 5);
		assertEquals(5, node.get("deep.testInt"));
		assertEquals(5, node.getSubNode("deep").getInt("testInt"));

		// Test number conversion
		node.set("testDouble", 2.5);
		assertEquals(2.5f, node.getFloat("testDouble"), 0.00001f);
		assertEquals(2.5, node.getDouble("testDouble"), 0.00001);
		assertEquals(-1, node.getInt("testDouble", -1));

		// Test keys
		assertEquals(ImmutableSet.<String> of("testInt", "deep", "testDouble"), node.getKeys());
		assertEquals(ImmutableSet.<String> of("testInt"), node.getKeys("deep"));
		assertEquals(ImmutableSet.<String> of(), node.getKeys("nonexisting.node"));

		// Test sub-nodes
		assertEquals(node.getSubNodes().size(), 1);
		assertEquals(node.getSubNodes().get(0), node.getSubNode("deep"));

		// Test getOrCreate
		node.getOrCreateSubNode("nonexisting.node").set("abc", "def");
		assertEquals("def", node.getString("nonexisting.node.abc"));

		// Test lists
		final List<Integer> list = ImmutableList.<Integer> of(1, 2, 3, 4, 5);
		node.set("list", list);
		assertEquals(list, node.getList("list"));

		// Test node lists
		node.set("nodeList", ImmutableList.of(ImmutableMap.of("a", 1), ImmutableMap.of("b", 2), ImmutableMap.of("c", 3), 4));
		int sum = 0;
		for (final DataNode subNode : node.getSubNodeList("nodeList"))
			for (final String key : subNode.getKeys())
				sum += subNode.getInt(key);
		assertEquals(6, sum);
	}

	@Test
	public void testFloatMathMix() {
		assertEquals(0f, FloatMath.mix(0f, 1f, 0f), 0.001f);
		assertEquals(1f, FloatMath.mix(0f, 1f, 1f), 0.001f);
		assertEquals(0.5f, FloatMath.mix(0f, 1f, 0.5f), 0.001f);

		assertEquals(100f, FloatMath.mix(0f, 1000f, 0.1f), 0.001f);
		assertEquals(3f, FloatMath.mix(1f, 2f, 2f), 0.001f);
	}

	@Test
	public void testFormatBytes() {
		assertEquals("1 byte", Utils.formatBytes(1));
		assertEquals("12 bytes", Utils.formatBytes(12));
		assertEquals("123 bytes", Utils.formatBytes(123));
		assertEquals("1.21 kB", Utils.formatBytes(1234));
		assertEquals("12.1 kB", Utils.formatBytes(12345));
		assertEquals("120 kB", Utils.formatBytes(123456));
		assertEquals("1.18 MB", Utils.formatBytes(1234567));
		assertEquals("11.8 MB", Utils.formatBytes(12345678));
		assertEquals("117 MB", Utils.formatBytes(123456789));
		assertEquals("1.15 GB", Utils.formatBytes(1234567890));
	}

	@Test
	public void testHaveSameElements() {
		assertTrue(Utils.haveSameElements(null, null));
		assertTrue(Utils.haveSameElements(null, Collections.emptyList()));
		assertTrue(Utils.haveSameElements(Collections.emptySet(), null));

		final List<String> list1 = new ArrayList<>();
		list1.add("Hallo");
		list1.add("Welt");

		assertTrue(Utils.haveSameElements(list1, list1));

		final List<String> list2 = new ArrayList<>(list1);
		list2.add("!");

		assertFalse(Utils.haveSameElements(list1, list2));

		list1.add("!");
		list1.add("Welt");

		assertFalse(Utils.haveSameElements(list1, list2));

		list2.add("Welt");
		assertTrue(Utils.haveSameElements(list1, list2));
	}

	@Test
	public void testHex() {
		final byte[] bytes = new byte[256];
		for (int i = 0; i < 256; i++)
			bytes[i] = (byte)i;

		assertArrayEquals(bytes, Utils.fromHex(Utils.toHex(bytes)));
	}

	@Test
	public void testIncludesExcludesPredicate() {
		final PatternSet includes = PatternSets.createPatternSet("*.jpg", "filme/*");
		final PatternSet excludes = PatternSets.createPatternSet("*.avi");

		final Predicate<File> predicate = new IncludesExcludesPredicate(includes, excludes, "C:/");

		assertTrue(predicate.test(new File("C:/Bilder/picture.jpg")));
		assertTrue(predicate.test(new File("C:\\Bilder\\picture.jpg")));// Same with backslashes

		assertTrue(predicate.test(new File("C:/Filme/movie.mkv"))); // Check first folder
		assertFalse(predicate.test(new File("D:/Filme/movie.mkv")));// Check different drive

		assertFalse(predicate.test(new File("C:/Filme/movie.avi"))); // Check exclude
	}

	@Test
	public void testIndexOfArray() {
		final String[] haystack = {"Hallo", "Welt"};

		assertEquals(0, Utils.indexOf(haystack, "Hallo"));
		assertEquals(1, Utils.indexOf(haystack, "Welt"));
		assertEquals(-1, Utils.indexOf(haystack, "hallo"));
	}

	@Test
	public void testIndexOfArrayIgnoreCase() {
		final String[] haystack = {"Hallo", "Welt"};

		assertEquals(0, Utils.indexOfIgnoreCase(haystack, "Hallo"));
		assertEquals(1, Utils.indexOfIgnoreCase(haystack, "Welt"));
		assertEquals(0, Utils.indexOfIgnoreCase(haystack, "hallo"));
		assertEquals(-1, Utils.indexOfIgnoreCase(haystack, "hello"));
	}

	@Test
	public void testIntMathMin() {
		assertEquals(1, IntMath.min(1));
		assertEquals(1, IntMath.min(1, 2));
		assertEquals(5, IntMath.min(5, 6, 7, 8));
		assertEquals(1, IntMath.min(2, 1, 3, 1));
		assertEquals(-1000, IntMath.min(2, 1, 3, -1000));
	}

	@Test
	public void testJoinCollection() {
		final List<String> values = new ArrayList<>();
		values.add("Hallo");
		values.add("Welt");

		assertEquals("Hallo Welt", Utils.join(values, ' '));
		assertEquals("", Utils.join(Collections.<String> emptyList(), ' '));

		assertEquals("Hallo - Welt", Utils.join(values, " - "));
		assertEquals("", Utils.join(Collections.<String> emptyList(), " - "));
	}

	@Test
	public void testLinearAlternatingFloat() {
		final FloatNumber number = new ModifiedFloat(new AlternatingFloat(0f, 100f, 1f, 0f), Modifiers.LINEAR);
		for (int i = 0; i < 3; i++) {
			for (float f = 0f; f < 100f; f++) {
				if (Math.abs(number.get() - f) > 0.00001f)
					fail("Wrong number increment: " + number.get() + " != " + f);
				number.next();
			}
			for (float f = 100f; f > 0f; f--) {
				if (Math.abs(number.get() - f) > 0.00001f)
					fail("Wrong number increment: " + number.get() + " != " + f);
				number.next();
			}
		}
	}

	@Test
	public void testListing() {
		assertEquals("1", Utils.listing(ImmutableList.of("1"), ", ", " and "));
		assertEquals("1 and 2", Utils.listing(ImmutableList.of("1", "2"), ", ", " and "));
		assertEquals("1, 2 and 3", Utils.listing(ImmutableList.of("1", "2", "3"), ", ", " and "));

		assertEquals("1", Utils.listing(new String[]{"1"}, ", ", " and "));
		assertEquals("1 and 2", Utils.listing(new String[]{"1", "2"}, ", ", " and "));
		assertEquals("1, 2 and 3", Utils.listing(new String[]{"1", "2", "3"}, ", ", " and "));
	}

	@Test
	public void testMax() {
		assertEquals("abcdefg", Utils.max(new String[]{"a", "ab", "abc", "abcdefg", ""}, s -> s.length()));
		// Inverse IntFunction to create min
		assertEquals("", Utils.max(new String[]{"a", "ab", "abc", "abcdefg", ""}, s -> -s.length()));
	}

	@Test
	public void testQuadraticAlternatingFloat() {
		final FloatNumber number = new ModifiedFloat(new AlternatingFloat(0f, 100f, 1f, 0f), Modifiers.QUADRATIC);
		for (int i = 0; i < 3; i++) {
			for (float f = 0f; f < 100f; f++) {
				if (Math.abs(number.get() - f / 100f * f) > 0.0001f)
					fail("Wrong number increment: " + number.get() + " != " + f / 100f * f);
				number.next();
			}
			for (float f = 100f; f > 0f; f--) {
				if (Math.abs(number.get() - f / 100f * f) > 0.0001f)
					fail("Wrong number increment: " + number.get() + " != " + f / 100f * f);
				number.next();
			}
		}
	}

	@Test
	public void testRomanNumerals() {
		assertEquals("I", Utils.romanNumerals(1));
		assertEquals("II", Utils.romanNumerals(2));
		assertEquals("III", Utils.romanNumerals(3));
		assertEquals("IV", Utils.romanNumerals(4));
		assertEquals("V", Utils.romanNumerals(5));
		assertEquals("VI", Utils.romanNumerals(6));
		assertEquals("VII", Utils.romanNumerals(7));
		assertEquals("VIII", Utils.romanNumerals(8));
		assertEquals("IX", Utils.romanNumerals(9));
		assertEquals("X", Utils.romanNumerals(10));
		assertEquals("XIII", Utils.romanNumerals(13));
		assertEquals("DCCC", Utils.romanNumerals(800));
		assertEquals("CMXCIX", Utils.romanNumerals(999));
		assertEquals("MCMLXXXIV", Utils.romanNumerals(1984));
		assertEquals("MCMXCIX", Utils.romanNumerals(1999));
		assertEquals("MMMDCCCLXXXVIII", Utils.romanNumerals(3888));
		assertEquals("MMMCMXCIX", Utils.romanNumerals(3999));
	}

	@Test
	public void testSplit() {
		final String[] testStrings = new String[]{
				"Hallo Welt!",
				"Hallo  Welt!",
				" Hallo Welt!",
				"Hallo Welt! ",
				"Hallo  Welt! ",
				"",
				" ",
				"  ",
				"                                ",
				"H a l l o   W e l t! "};

		for (final String str : testStrings)
			assertArrayEquals("'" + str + "'", str.split(" "), Utils.split(str, ' '));
	}

	@Test
	public void testTimeSpecParser() throws ParseException {
		assertEquals(99, TimeSpecParser.parseTimeSpec("99"));
		assertEquals(5, TimeSpecParser.parseTimeSpec("5m"));

		assertEquals(13144, TimeSpecParser.parseTimeSpec("1w2d3h4m"));
		assertEquals(0, TimeSpecParser.parseTimeSpec("0m"));
		assertEquals(240, TimeSpecParser.parseTimeSpec("2h120m"));
		assertEquals(5, TimeSpecParser.parseTimeSpec("5m"));

		assertEquals(123, TimeSpecParser.parseTimeSpec("123", "mins"));

		assertEquals(5, TimeSpecParser.parseTimeSpec("1m", "1m", "1m", "1m", "1m"));
	}

	@Test
	public void testToPrimitive() throws NotANumberException {
		// Int
		assertEquals(123, toInt(123));
		assertEquals(123, toInt((long)123));
		assertEquals(123, toInt("123"));
		assertEquals(-1, toInt("123q", -1));
		assertFalse(isInt(null));
		assertFalse(isInt("123q"));
		assertFalse(isInt("123.4"));
		assertEquals(Integer.MAX_VALUE, toInt((long)Integer.MAX_VALUE));
		assertFalse(isInt((long)Integer.MAX_VALUE + 1)); // Check overflow
		assertFalse(isInt(123.4f));
		assertFalse(isInt(123.4));

		// Long
		assertEquals(123, toLong("123"));
		assertEquals(-1, toLong("123q", -1));
		assertEquals(Long.MAX_VALUE, toLong(Long.MAX_VALUE));
		assertFalse(isLong(null));
		assertFalse(isLong("123q"));
		assertFalse(isLong("123.4"));
		assertEquals(Long.MAX_VALUE, toLong(Long.MAX_VALUE));
		assertFalse(isLong(Long.MAX_VALUE * 2d)); // Check overflow
		assertFalse(isLong(123.4f));
		assertFalse(isLong(123.4));

		// Float
		assertEquals(123.4f, toFloat("123.4"), 0.0001f);
		assertEquals(-1, toFloat("123.xyz", -1), 0.0001f);
		assertFalse(isFloat(null));
		assertFalse(isFloat(Float.NaN));
		assertFalse(isFloat("123q"));
		assertEquals(Float.MAX_VALUE, toFloat(Float.MAX_VALUE), 0.0001f);
		assertFalse(isFloat(Float.MAX_VALUE * 2d));
		assertFalse(isFloat(Double.MAX_VALUE));
		assertTrue(isFloat(-1));

		// Double
		assertEquals(123.4f, toDouble("123.4", -1), 0.0001);
		assertEquals(-1, toDouble("123.xyz", -1), 0.0001);
		assertEquals(Byte.MAX_VALUE, toDouble(Byte.MAX_VALUE), 0.0001);
		assertEquals(Integer.MAX_VALUE, toDouble(Integer.MAX_VALUE), 0.0001);
		assertEquals(Long.MAX_VALUE, toDouble(Long.MAX_VALUE), 0.0001);
		assertEquals(Float.MAX_VALUE, toDouble(Float.MAX_VALUE), 0.0001);
		assertEquals(Double.MAX_VALUE, toDouble(Double.MAX_VALUE), 0.0001);
		assertFalse(isDouble(Double.NaN));

		// Boolean
		assertEquals(true, toBoolean("true"));
		assertEquals(false, toBoolean("FALSE"));
		assertEquals(false, toBoolean(0));
		assertEquals(true, toBoolean(1));
		assertEquals(true, toBoolean(1L));
		assertEquals(true, toBoolean((byte)1));
		assertFalse(isBoolean(2));
		assertTrue(isBoolean(1.0f));
		assertFalse(isBoolean(1.1f));
	}

	@Test
	public void testUtilsTrail() {
		assertEquals("Hallo Welt", Utils.tail("Hallo", " Welt"));
		assertEquals("Hallo Welt", Utils.tail("Hallo Welt", " Welt"));

		assertEquals("Hallo Welt!", Utils.tail("Hallo Welt", '!'));
		assertEquals("Hallo Welt!", Utils.tail("Hallo Welt!", '!'));

		assertEquals("C:/hallo/welt/", Utils.tailingSlash("C:/hallo/welt"));
		assertEquals("C:/hallo/welt\\", Utils.tailingSlash("C:/hallo/welt\\"));
		assertEquals("C:/hallo/welt/", Utils.tailingSlash("C:/hallo/welt/"));
	}

	@Test
	public void testWeakArrayInterner() {
		// Create two string arrays
		final String[] arr1 = new String[]{"a", "b", "c"}, arr2 = new String[]{"a", "b", "c"};
		// These are distinct objects
		assertFalse(arr1 == arr2);

		// Apply them both to the array interner
		final Interner<String[]> interner = new WeakArrayInterner<>();
		final String[] arr1Interned = interner.intern(arr1), arr2Interned = interner.intern(arr2);

		// Now they are the same object
		assertTrue(arr1Interned == arr2Interned);
	}

	@Test
	public void testWildcardPattern() {
		WildcardPattern pattern = WildcardPatterns.compile("sha");
		assertEquals(true, pattern.matches("sha"));
		assertEquals(false, pattern.matches("md5"));
		assertEquals(false, pattern.matches("sha1"));

		pattern = WildcardPatterns.compile("*.sha");
		assertEquals(true, pattern.matches("checksum.sha"));
		assertEquals(false, pattern.matches("checksum.md5"));
		assertEquals(false, pattern.matches("checksum.sha1"));

		pattern = WildcardPatterns.compile("checksum*");
		assertEquals(true, pattern.matches("checksum.sha"));
		assertEquals(false, pattern.matches("crc.md5"));

		pattern = WildcardPatterns.compile("c*.sha");
		assertEquals(true, pattern.matches("checksum.sha"));
		assertEquals(false, pattern.matches("checksum.md5"));
		assertEquals(false, pattern.matches("checksum.sha1"));

		pattern = WildcardPatterns.compile("*");
		assertEquals(true, pattern.matches("Hallo Welt!"));

		pattern = WildcardPatterns.compile("*a*");
		assertEquals(true, pattern.matches("Hallo Welt!"));
		assertEquals(false, pattern.matches("Hello World!"));

		pattern = WildcardPatterns.compile("*Hallo*");
		assertEquals(true, pattern.matches("Hallo Welt!"));
		assertEquals(false, pattern.matches("Hello World!"));

		pattern = WildcardPatterns.compile("*Hallo*Welt");
		assertEquals(true, pattern.matches("Hallo Welt"));
		assertEquals(false, pattern.matches("Hallo Welt!"));

		pattern = WildcardPatterns.compile("c*.*");
		assertEquals(true, pattern.matches("checksum.sha"));
		assertEquals(true, pattern.matches("checksum.md5"));
		assertEquals(false, pattern.matches("acrc.sha1"));

		pattern = WildcardPatterns.compile("*a*l* *e*t*");
		assertEquals(true, pattern.matches("Hallo Welt!"));

		pattern = WildcardPatterns.compile("H*l*o*W*l*!");
		assertEquals(true, pattern.matches("Hallo Welt!"));

		pattern = WildcardPatterns.compile("*");
		assertEquals(true, pattern.matches("Hallo Welt!"));
		assertEquals(true, pattern.matches("checksum.sha"));
		assertEquals(true, pattern.matches("checksum.md5"));
	}

	@Test
	public void testWildcardPatternsMatchAll() {
		final PatternSet patternSet = PatternSets.createPatternSet(
				WildcardPatterns.compile("*.sha"),
				WildcardPatterns.compile("checksum*"));

		assertEquals(true, patternSet.matchesAll("checksum.sha"));
		assertEquals(false, patternSet.matchesAll("checksum.md5"));
		assertEquals(false, patternSet.matchesAll("checksum.txt"));
	}

	@Test
	public void testWildcardPatternsMatchAny() {
		final PatternSet patternSet = PatternSets.createPatternSet(
				WildcardPatterns.compile("*.sha"),
				WildcardPatterns.compile("*.md5"));

		assertEquals(true, patternSet.matchesAny("checksum.sha"));
		assertEquals(true, patternSet.matchesAny("checksum.md5"));
		assertEquals(false, patternSet.matchesAny("checksum.txt"));
	}

	@Test
	public void testZip() throws IOException, URISyntaxException {
		final File zipFile = new File(Files.createTempDir(), "tmp.zip");

		try (SimpleFileSystem zip = SimpleFileSystem.createZipFileSystem(zipFile)) {

			{// Test basic streams
				final String fileName = "mydata.dat";
				final byte[] data = {2, 3, 5, 7, 11, 13, 17, 9};

				assertFalse(zip.exists(fileName));

				try (OutputStream os = zip.openOutputStream(fileName)) {
					os.write(data);
				}

				assertTrue(zip.exists(fileName));

				try (InputStream is = zip.openInputStream(fileName)) {
					final byte[] buf = new byte[data.length];
					is.read(buf);
					assertArrayEquals(data, buf);
				}
			}

			{// Test basic writing and reading
				final String fileName = "myfile.txt", msg = "Hallo Welt!";

				assertFalse(zip.exists(fileName));

				try (Writer writer = zip.openWriter(fileName, Charsets.UTF_8)) {
					writer.write(msg);
				}

				assertTrue(zip.exists(fileName));

				try (Reader reader = zip.openReader(fileName, Charsets.UTF_8)) {
					assertEquals(msg, Utils.read(reader));
				}
			}

			{// Test deep writing and reading
				final String fileName = "deep/folder/myfile.txt", msg = "Hallo Welt!";

				assertFalse(zip.exists(fileName));

				try (Writer writer = zip.openWriter(fileName, Charsets.UTF_8)) {
					writer.write(msg);
				}

				assertTrue(zip.exists(fileName));

				try (Reader reader = zip.openReader(fileName, Charsets.UTF_8)) {
					assertEquals(msg, Utils.read(reader));
				}
			}

			{// Test reading of non-existing files
				final String fileName = "file/that/does/not/exist.exe";

				assertFalse(zip.exists(fileName));

				try (Reader reader = zip.openReader(fileName, Charsets.UTF_8)) {
					fail("NoSuchFileException wasn't thrown");
				} catch (final NoSuchFileException ex) {}
			}
		}
	}
}
