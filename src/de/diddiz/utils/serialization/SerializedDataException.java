package de.diddiz.utils.serialization;

import java.io.IOException;

/**
 * @author Robin Kupper
 */
public class SerializedDataException extends IOException
{
	public SerializedDataException(String message) {
		super(message);
	}

	public SerializedDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
