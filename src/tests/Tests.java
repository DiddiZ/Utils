package tests;

import static org.junit.Assert.fail;
import junit.framework.Assert;
import org.junit.Test;
import de.diddiz.utils.Utils;
import de.diddiz.utils.numbers.AlternatingFloat;
import de.diddiz.utils.numbers.FloatNumber;
import de.diddiz.utils.numbers.ModifiedFloat;
import de.diddiz.utils.speedmodifiers.SpeedModifiers;

@SuppressWarnings("static-method")
public class Tests
{
	@Test
	public void testFormatBytes() {
		Assert.assertEquals("1 byte", Utils.formatBytes(1));
		Assert.assertEquals("12 bytes", Utils.formatBytes(12));
		Assert.assertEquals("123 bytes", Utils.formatBytes(123));
		Assert.assertEquals("1.21 kB", Utils.formatBytes(1234));
		Assert.assertEquals("12.1 kB", Utils.formatBytes(12345));
		Assert.assertEquals("120 kB", Utils.formatBytes(123456));
		Assert.assertEquals("1.18 MB", Utils.formatBytes(1234567));
		Assert.assertEquals("11.8 MB", Utils.formatBytes(12345678));
		Assert.assertEquals("117 MB", Utils.formatBytes(123456789));
		Assert.assertEquals("1.15 GB", Utils.formatBytes(1234567890));
	}

	@Test
	public void testLinearAlternatingFloat() {
		final FloatNumber number = new ModifiedFloat(new AlternatingFloat(0f, 100f, 1f, 0f), SpeedModifiers.LINEAR);
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
		final FloatNumber number = new ModifiedFloat(new AlternatingFloat(0f, 100f, 1f, 0f), SpeedModifiers.QUADRATIC);
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
}
