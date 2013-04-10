package de.diddiz.utils;

public interface ProgressListener
{
	/**
	 * @param position Current position in the task. Upper bound is not necessarily known.
	 */
	public void onProgress(long position);
}
