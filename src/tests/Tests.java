package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import de.diddiz.utils.TimeSpecParser;
import de.diddiz.utils.Utils;
import de.diddiz.utils.modifiers.Modifiers;
import de.diddiz.utils.numbers.AlternatingFloat;
import de.diddiz.utils.numbers.FloatNumber;
import de.diddiz.utils.numbers.ModifiedFloat;
import de.diddiz.utils.wildcards.WildcardPattern;
import de.diddiz.utils.wildcards.WildcardPatterns;

@SuppressWarnings("static-method")
public class Tests
{
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

		pattern = WildcardPatterns.compile("c*.*");
		assertEquals(true, pattern.match("checksum.sha"));
		assertEquals(true, pattern.match("checksum.md5"));
		assertEquals(false, pattern.match("acrc.sha1"));

		pattern = WildcardPatterns.compile("*a*l* *e*t*");
		assertEquals(true, pattern.match("Hallo Welt!"));

		pattern = WildcardPatterns.compile("H*l*o*W*l*!");
		assertEquals(true, pattern.match("Hallo Welt!"));
	}

	@Test
	public void testWildcardPatternsMatchAll() {
		final List<WildcardPattern> patterns = new ArrayList<>();
		patterns.add(WildcardPatterns.compile("*.sha"));
		patterns.add(WildcardPatterns.compile("checksum*"));

		assertEquals(true, WildcardPatterns.matchAll(patterns, "checksum.sha"));
		assertEquals(false, WildcardPatterns.matchAll(patterns, "checksum.md5"));
		assertEquals(false, WildcardPatterns.matchAll(patterns, "checksum.txt"));
	}

	@Test
	public void testWildcardPatternsMatchAny() {
		final List<WildcardPattern> patterns = new ArrayList<>();
		patterns.add(WildcardPatterns.compile("*.sha"));
		patterns.add(WildcardPatterns.compile("*.md5"));

		assertEquals(true, WildcardPatterns.matchAny(patterns, "checksum.sha"));
		assertEquals(true, WildcardPatterns.matchAny(patterns, "checksum.md5"));
		assertEquals(false, WildcardPatterns.matchAny(patterns, "checksum.txt"));
	}
}
