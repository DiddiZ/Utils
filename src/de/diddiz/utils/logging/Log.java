package de.diddiz.utils.logging;

import static de.diddiz.utils.Utils.NEWLINE;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import de.diddiz.utils.Utils;

/**
 * Simple logging facility that focuses on a neat style.
 * Uses {@link System#out} as default output. Log files may be added.
 */
public final class Log
{
	private static final Logger logger = Logger.getLogger(Log.class.getName());
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static final Formatter logFormatter = new Formatter() {
		@Override
		public synchronized String format(LogRecord logRecord) {
			return (dateFormat != null ? dateFormat.format(logRecord.getMillis()) + " " : "") +
					"[" + logRecord.getLevel() + "] " +
					logRecord.getMessage() + NEWLINE +
					(logRecord.getThrown() != null ? Utils.toString(logRecord.getThrown()) : "");
		}
	};

	static {
		logger.setUseParentHandlers(false); // Get complete control over the logger
		logger.addHandler(new ConsoleHandler() { // Add our own style
			{
				setFormatter(logFormatter); // Use our own formatter
				setOutputStream(System.out); // Use default output
			}
		});
	}

	/**
	 * Will append all log output to the specified file.
	 */
	public static void addLogFile(File logfile) throws SecurityException, IOException {
		final FileHandler fileHandler = new FileHandler(logfile.getAbsolutePath(), true);
		fileHandler.setFormatter(logFormatter);
		logger.addHandler(fileHandler);
	}

	/**
	 * Returns the {@link Logger} used for all logging.
	 * Please don't alter the logger by changing the log level or something.
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * Shorthand for:
	 * 
	 * <pre>
	 * Log.getLogger().info(msg);
	 * </pre>
	 */
	public static void info(String msg) {
		logger.info(msg);
	}

	/**
	 * Changes the {@link DateFormat} prefix for all lines.
	 * Default is <code>HH:mm:ss</code>.
	 * 
	 * @param dateFormat May be {@code null} to disable time prefix.
	 */
	public static synchronized void setDateFormat(DateFormat dateFormat) {
		Log.dateFormat = dateFormat;
	}

	/**
	 * Shorthand for:
	 * 
	 * <pre>
	 * Log.getLogger().severe(msg);
	 * </pre>
	 */
	public static void severe(String msg) {
		logger.severe(msg);
	}

	/**
	 * Shorthand for:
	 * 
	 * <pre>
	 * Log.getLogger().log(Level.SEVERE, msg, thrown);
	 * </pre>
	 */
	public static void severe(String msg, Throwable thrown) {
		logger.log(Level.SEVERE, msg, thrown);
	}

	/**
	 * Shorthand for:
	 * 
	 * <pre>
	 * Log.getLogger().warning(msg, thrown);
	 * </pre>
	 */
	public static void warning(String msg) {
		logger.warning(msg);
	}

	/**
	 * Shorthand for:
	 * 
	 * <pre>
	 * Log.getLogger().log(Level.WARNING, msg, thrown);
	 * </pre>
	 */
	public static void warning(String msg, Throwable thrown) {
		logger.log(Level.WARNING, msg, thrown);
	}
}
