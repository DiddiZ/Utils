package de.diddiz.utils.math;

public class Averager
{
	private final int len;
	private final float[] data;
	private float average;
	private int pos;
	private boolean populated;

	public Averager(int len) {
		this.len = len;
		data = new float[len];
	}

	public float calcAverage() {
		float sum = 0;
		for (int i = 0; i < len; i++)
			sum += data[i];
		average = sum / len;
		return average;
	}

	public float getAverage() {
		return average;
	}

	public boolean isPopulated() {
		return populated;
	}

	public void reset() {
		average = 0;
		for (int i = 0; i < len; i++)
			data[i] = 0;
		populated = false;
	}

	public void update(float value) {
		average -= data[pos] / len;
		average += value / len;
		data[pos] = value;
		pos++;
		if (pos >= len) {
			pos = 0;
			populated = true;
		}
	}
}
