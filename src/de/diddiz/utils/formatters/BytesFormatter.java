package de.diddiz.utils.formatters;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Robin Kupper
 */
public class BytesFormatter implements Formatter<Long>
{
	public static final BytesFormatter INSTANCE = new BytesFormatter();

	private final DecimalFormat decimalFormat1 = new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.US)),
			decimalFormat2 = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.US));

	@Override
	public String format(Long bytes) {
		return formatBytes(bytes != null ? bytes : 0);
	}

	public String formatBytes(long bytes) {
		if (bytes < 0)
			throw new IllegalArgumentException("bytes can't be negative");
		if (bytes == 1)
			return "1 byte";
		if (bytes < 1024)
			return bytes + " bytes";
		if (bytes < 10240)
			return decimalFormat2.format(bytes / 1024D) + " kB";
		if (bytes < 102400)
			return decimalFormat1.format(bytes / 1024D) + " kB";
		if (bytes < 1048576)
			return bytes / 1024 + " kB";
		if (bytes < 10485760)
			return decimalFormat2.format(bytes / 1048576D) + " MB";
		if (bytes < 104857600)
			return decimalFormat1.format(bytes / 1048576D) + " MB";
		if (bytes < 1073741824)
			return bytes / 1048576 + " MB";
		if (bytes < 10737418240L)
			return decimalFormat2.format(bytes / 1073741824D) + " GB";
		if (bytes < 107374182400L)
			return decimalFormat1.format(bytes / 1073741824D) + " GB";
		return bytes / 1073741824 + " GB";
	}
}
