package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import de.diddiz.utils.TimeSpecParser;
import de.diddiz.utils.Utils;
import de.diddiz.utils.modifiers.Modifiers;
import de.diddiz.utils.numbers.AlternatingFloat;
import de.diddiz.utils.numbers.FloatNumber;
import de.diddiz.utils.numbers.ModifiedFloat;
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
	public void testWildcardPattern() {
		WildcardPattern pattern = WildcardPatterns.compile("sha");
		assertEquals(true, pattern.match("sha"));
		assertEquals(false, pattern.match("md5"));
		assertEquals(false, pattern.match("sha1"));

		pattern = WildcardPatterns.compile("*.sha");
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(false, pattern.match("checksum.md5"));
		assertEquals(false, pattern.match("checksum.sha1"));

		pattern = WildcardPatterns.compile("checksum*");
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(false, pattern.match("crc.md5"));

		pattern = WildcardPatterns.compile("c*.sha");
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(false, pattern.match("checksum.md5"));
		assertEquals(false, pattern.match("checksum.sha1"));

		pattern = WildcardPatterns.compile("*");
		assertEquals(true, pattern.match("Hallo Welt!"));

		pattern = WildcardPatterns.compile("*a*");
		assertEquals(true, pattern.match("Hallo Welt!"));
		assertEquals(false, pattern.match("Hello World!"));

		pattern = WildcardPatterns.compile("*Hallo*");
		assertEquals(true, pattern.match("Hallo Welt!"));
		assertEquals(false, pattern.match("Hello World!"));

		pattern = WildcardPatterns.compile("*Hallo*Welt");
		assertEquals(true, pattern.match("Hallo Welt"));
		assertEquals(false, pattern.match("Hallo Welt!"));

		pattern = WildcardPatterns.compile("c*.*");
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(true, pattern.match("checksum.md5"));
		assertEquals(false, pattern.match("acrc.sha1"));

		pattern = WildcardPatterns.compile("*a*l* *e*t*");
		assertEquals(true, pattern.match("Hallo Welt!"));

		pattern = WildcardPatterns.compile("H*l*o*W*l*!");
		assertEquals(true, pattern.match("Hallo Welt!"));

		pattern = WildcardPatterns.compile("*");
		assertEquals(true, pattern.match("Hallo Welt!"));
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(true, pattern.match("checksum.md5"));
	}

	@Test
	public void testWildcardPatternsMatchAll() {
		final PatternSet patternSet = PatternSets.createPatternSet(
				WildcardPatterns.compile("*.sha"),
				WildcardPatterns.compile("checksum*"));

		assertEquals(true, patternSet.matchAll("checksum.sha"));
		assertEquals(false, patternSet.matchAll("checksum.md5"));
		assertEquals(false, patternSet.matchAll("checksum.txt"));
	}

	@Test
	public void testWildcardPatternsMatchAny() {
		final PatternSet patternSet = PatternSets.createPatternSet(
				WildcardPatterns.compile("*.sha"),
				WildcardPatterns.compile("*.md5"));

		assertEquals(true, patternSet.matchAny("checksum.sha"));
		assertEquals(true, patternSet.matchAny("checksum.md5"));
		assertEquals(false, patternSet.matchAny("checksum.txt"));
	}
}
