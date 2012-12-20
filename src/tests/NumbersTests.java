package tests;

import static org.junit.Assert.fail;
import org.junit.Test;
import de.diddiz.utils.numbers.FloatNumber;
import de.diddiz.utils.numbers.ModifiedAlternatingFloat;
import de.diddiz.utils.speedmodifiers.SpeedModifiers;

public class NumbersTests
{

	@SuppressWarnings("static-method")
	@Test
	public void testcubic() {
		final FloatNumber number = new ModifiedAlternatingFloat(0f, 100f, 1f, 0f, SpeedModifiers.LINEAR);
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

	@SuppressWarnings("static-method")
	@Test
	public void testLinear() {
		final FloatNumber number = new ModifiedAlternatingFloat(0f, 100f, 1f, 0f, SpeedModifiers.QUADRATIC);
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
