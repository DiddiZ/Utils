package de.diddiz.utils.arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Pool of byte arrays with a fixed length. To prevent pollution this pool has a maximum number of kept arrays.
 *
 * @author Robin Kupper
 */
public class ByteArrayPool
{
	private final int arrayLength;
	private final int maxPoolSize;
	private final List<byte[]> pool;

	/**
	 * @param arrayLength All arrays are of this length.
	 * @param maxPoolSize Number of kept arrays.
	 */
	public ByteArrayPool(int arrayLength, int maxPoolSize) {
		this.arrayLength = arrayLength;
		this.maxPoolSize = maxPoolSize;

		pool = new ArrayList<>(maxPoolSize);
	}

	/**
	 * Returns an array to the pool. If the pool is full the array is forsaken.
	 *
	 * @throws IllegalArgumentException If the supplied array has a different length than this pool requires
	 */
	public void add(byte[] array) throws IllegalArgumentException {
		if (array.length != arrayLength)
			throw new IllegalArgumentException("Supplied array has a length of " + array.length + " while this pool enforces a length of " + arrayLength);
		if (pool.size() < maxPoolSize)
			pool.add(array);
	}

	/**
	 * Either retrieves an array from the pool or creates a new one if the pool is empty.
	 */
	public byte[] get() {
		if (pool.size() > 0)
			return pool.remove(pool.size() - 1);
		return new byte[arrayLength];
	}
}
