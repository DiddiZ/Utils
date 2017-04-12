package de.diddiz.utils;

import static de.diddiz.utils.Utils.isInt;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Robin Kupper
 */
public final class TimeSpecParser
{
	private static final int MILLIS_PER_MINUTE = 1000 * 60;
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy"),
			DATE_TIME_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	/**
	 * Accepts various time formats.
	 * Mentionable are: Plain integer ({@code "5"}), integer and unit ({@code "3", "hours"}), wdhm format ({@code "1w3d6h15m"}), dates ({@code "21.08.1992"}), times ({@code "12:30:45"}), and datetimes ({@code "21.08.1992", "12:30:45"}).
	 *
	 * @return specified minutes or difference from now to specified time stamp in minutes.
	 * @throws ParseException if unable to parse time spec.
	 */
	public static synchronized int parseTimeSpec(String... spec) throws ParseException {
		if (spec == null || spec.length < 1)
			throw new IllegalArgumentException("Supplied time spec is empty");

		// If we just get one value without unit we assume it's minutes
		if (spec.length == 1 && isInt(spec[0]))
			return Integer.parseInt(spec[0]);

		// If we get a single time we assume it's today
		if (spec.length == 1 && spec[0].contains(":"))
			return (int)((DATE_TIME_FORMAT.parse(DATE_FORMAT.format(System.currentTimeMillis()) + " " + spec[0]).getTime() - System.currentTimeMillis()) / MILLIS_PER_MINUTE);
		// If we get a single time we assume it's today
		if (spec.length == 1 && spec[0].contains("."))
			return (int)((DATE_TIME_FORMAT.parse(spec[0] + " 00:00:00").getTime() - System.currentTimeMillis()) / MILLIS_PER_MINUTE);
		// Check if we got a full time stamp
		if (spec.length == 2 && spec[0].contains(".") && spec[1].contains(":"))
			return (int)((DATE_TIME_FORMAT.parse(spec[0] + " " + spec[1]).getTime() - System.currentTimeMillis()) / MILLIS_PER_MINUTE);

		// Check for formats like "5 weeks" or "23 min"
		if (spec.length == 2 && isInt(spec[0]) && isUnit(spec[1].charAt(0)))
			return Integer.parseInt(spec[0]) * getUnitMultiplier(spec[1].charAt(0));

		// Lastly check if we got something like "5w3d 1h". Will also match "1 h 2 m" and similar formats
		if (isDigit(spec[0].charAt(0)) && isUnit(spec[spec.length - 1].charAt(spec[spec.length - 1].length() - 1)))
			return parseWDHM(Utils.join(spec));

		throw new ParseException("Found no possible matching time format", 0);
	}

	private static int getUnitMultiplier(char c) {
		switch (c) {
			case 'w':
				return 60 * 24 * 7;
			case 'd':
				return 60 * 24;
			case 'h':
				return 60;
			case 'm':
				return 1;
		}
		throw new IllegalArgumentException("Unknown unit '" + c + "'");
	}

	private static boolean isDigit(char c) {
		return c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '0';
	}

	private static boolean isUnit(char c) {
		return c == 'w' || c == 'd' || c == 'h' || c == 'm';
	}

	/**
	 * Accepts a a time spec in form of {@code 1w2d3h4m} (1 week, 2 days, 3 hours and 4 minutes).
	 * <p>
	 * Every unit may appear multiple times (though it doesn't really makes sense), values will be added.
	 * </p> <p>
	 * Mustn't contain any char besides digits and {@code wdhm}, and at least one digit must precede {@code wdhm}, otherwise a {@code ParseException} will be thrown.
	 * </p> <p>
	 * Valid examples: {@code 1w}, {@code 0m}, {@code 1d1d},{@code 2h120m} </p> <p>
	 * Illegal examples: {@code 1wd}, {@code -1m}, {@code 1week}, {@code abcdefg} </p>
	 *
	 * @return minutes
	 */
	private static int parseWDHM(String str) throws ParseException {
		int minutes = 0;
		boolean found = false;

		int digits = 0; // Number of consecutive digits
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			if (isDigit(c))
				digits++;
			else if (isUnit(c)) {
				if (digits == 0)
					throw new ParseException("No number specified", i);
				minutes += Integer.parseInt(str.substring(i - digits, i)) * getUnitMultiplier(c);
				digits = 0;
				found = true;
			} else
				throw new ParseException("Unknown unit '" + c + "'", i);
		}
		if (!found)
			throw new ParseException("No date descriptors found", str.length());
		return minutes;
	}
}
