package de.diddiz.utils.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import de.diddiz.utils.Utils;
import de.diddiz.utils.serialization.SerializedData;
import de.diddiz.utils.serialization.SerializedDataException;

/**
 * @author Robin Kupper
 */
public abstract class PropertiesConfig extends SerializedData<String, String>
{
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

	@Override
	public String get(String key) {
		return properties.getProperty(key);
	}

	public File getConfigFile() {
		return file;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Enum<?> getEnum(String key, Class<? extends Enum> enumType) throws SerializedDataException {
		try {
			return Enum.valueOf(enumType, getChecked(key).toUpperCase());
		} catch (final IllegalArgumentException ex) {
			throw new SerializedDataException("Unknown constant '" + get(key) + "' for '" + key + "'. Allowed are: " + Arrays.asList(enumType.getEnumConstants()));
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Enum<?>> T getEnum(String key, T def) throws SerializedDataException {
		try {
			return (T)Enum.valueOf(def.getClass(), getChecked(key).toUpperCase());
		} catch (final IllegalArgumentException ex) {
			return def;
		}
	}

	public List<File> getFileList(String key) throws SerializedDataException {
		final List<String> strings = getList(key);
		final List<File> files = new ArrayList<>(strings.size());
		for (final String str : strings)
			files.add(new File(str));
		return files;
	}

	@Override
	public List<String> getList(String key) throws SerializedDataException {
		final String str = getChecked(key);
		if (str != null && str.length() > 0) {
			final String[] arr = str.split(";");
			for (int i = 0; i < arr.length; i++)
				arr[i] = arr[i].trim();
			return Arrays.asList(arr);
		}
		return Collections.emptyList();
	}

	public boolean isModified() {
		return modified;
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

	public void set(String key, double value) {
		set(key, String.valueOf(value));
	}

	public void set(String key, float value) {
		set(key, String.valueOf(value));
	}

	public void set(String key, int value) {
		set(key, String.valueOf(value));
	}

	public void set(String key, List<String> list) {
		set(key, String.valueOf(Utils.join(list, ';')));
	}

	public void set(String key, long value) {
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
