package de.diddiz.utils.config;

import static de.diddiz.utils.Utils.toBoolean;
import static de.diddiz.utils.Utils.toFloat;
import static de.diddiz.utils.Utils.toInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import de.diddiz.utils.math.NotANumberException;

public abstract class PropertiesConfig
{
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	private final Properties properties;
	private final File file;
	private boolean modified;

	public PropertiesConfig(File cfgFile) throws IOException {
		file = cfgFile;
		properties = new Properties();
		load();
	}

	/**
	 * Copies all non-existent entries from the default properties to the actual file.
	 */
	public PropertiesConfig(File cfgFile, Properties defaultProperties) throws IOException {
		file = cfgFile;

		properties = new Properties(defaultProperties);
		load();
		for (final String key : defaultProperties.stringPropertyNames())
			if (!properties.containsKey(key))
				set(key, defaultProperties.getProperty(key));
		save();
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Get an ";" separated {@code String} array.
	 */
	public String[] getArray(String key) {
		final String str = get(key);
		if (str != null && str.length() > 0) {
			final String[] arr = str.split(";");
			for (int i = 0; i < arr.length; i++)
				arr[i] = arr[i].trim();
			return arr;
		}
		return EMPTY_STRING_ARRAY;
	}

	public boolean getBoolean(String key) throws ConfigException {
		try {
			return toBoolean(get(key));
		} catch (final NotANumberException ex) {
			throw new ConfigException("Failed to read config key '" + key + "': " + ex.getMessage());
		}
	}

	public boolean getBoolean(String key, boolean def) {
		return toBoolean(get(key), def);
	}

	public File getConfigFile() {
		return file;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Enum<?> getEnum(String key, Class<? extends Enum> enumType) throws IOException {
		try {
			return Enum.valueOf(enumType, get(key).toUpperCase());
		} catch (final IllegalArgumentException ex) {
			throw new IOException("Unknown constant '" + get(key) + "' for '" + key + "'. Allowed are: " + Arrays.asList(enumType.getEnumConstants()));
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Enum<?>> T getEnum(String key, T def) {
		try {
			return (T)Enum.valueOf(def.getClass(), get(key).toUpperCase());
		} catch (final IllegalArgumentException ex) {
			return def;
		}
	}

	public File[] getFileArray(String key) {
		final String[] strings = getArray(key);
		final File[] files = new File[strings.length];
		for (int i = 0; i < strings.length; i++)
			files[i] = new File(strings[i]);
		return files;
	}

	public float getFloat(String key) throws ConfigException {
		try {
			return toFloat(get(key));
		} catch (final NotANumberException ex) {
			throw new ConfigException("Failed to read config key '" + key + "': " + ex.getMessage());
		}
	}

	public float getFloat(String key, float def) {
		return toFloat(get(key), def);
	}

	public int getInt(String key) throws ConfigException {
		try {
			return toInt(get(key));
		} catch (final NotANumberException ex) {
			throw new ConfigException("Failed to read config key '" + key + "': " + ex.getMessage());
		}
	}

	public int getInt(String key, int def) {
		return toInt(get(key), def);
	}

	public boolean isModified() {
		return modified;
	}

	/**
	 * @return Whether the specific key has any value attached.
	 */
	public boolean isSet(String key) {
		return get(key) != null;
	}

	public void remove(String key) {
		if (properties.containsKey(key)) {
			properties.remove(key);
			modified = true;
		}
	}

	public void save() throws IOException {
		if (modified) {
			try (final OutputStream stream = new FileOutputStream(file)) {
				properties.store(stream, null);
			}
			modified = false;
		}
	}

	public void set(String key, boolean value) {
		set(key, String.valueOf(value));
	}

	public void set(String key, int value) {
		set(key, String.valueOf(value));
	}

	public void set(String key, String value) {
		if (!value.equals(properties.get(key))) {
			properties.put(key, value);
			modified = true;
		}
	}

	private void load() throws IOException {
		if (!file.exists())
			file.createNewFile();
		try (final InputStream stream = new FileInputStream(file)) {
			properties.load(stream);
		}
		modified = false;
	}

	/**
	 * Convenient method to easily read Properties from a resource file (most likely stored inside the jar).
	 * 
	 * @param resourceName Resource name
	 */
	protected static Properties loadPropertiesFromResource(String resourceName) throws IOException {
		return loadPropertiesFromResource(resourceName, null);
	}

	/**
	 * Convenient method to easily read Properties from a resource file (most likely stored inside the jar).
	 * 
	 * @param resourceName Resource name
	 * @param parent May be null
	 */
	protected static Properties loadPropertiesFromResource(String resourceName, Properties parent) throws IOException {
		final URL propURL = System.class.getResource("/" + resourceName);
		if (propURL == null)
			throw new FileNotFoundException("Can't find resource " + resourceName);

		final Properties prop = parent != null ? new Properties(parent) : new Properties();
		try (final InputStream stream = propURL.openStream()) {
			prop.load(stream);
		}
		return prop;
	}
}
