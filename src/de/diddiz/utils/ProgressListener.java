package de.diddiz.utils;

public interface ProgressListener
{
	/**
	 * Called after the tast finishes.
	 */
	public void onFinish();

	/**
	 * @param position Current position in the task. Upper bound is not necessarily known.
	 */
	public void onProgress(long position);

	/**
	 * Called before the task starts.
	 */
	public void onStart();
}
