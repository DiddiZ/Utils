package de.diddiz.utils;


public class HexMath
{
	public static int distance(int x1, int y1, int x2, int y2) {
		final int dx = y2 - floor2(x2) - (y1 - floor2(x1));
		final int dy = y2 + ceil2(x2) - (y1 + ceil2(x1));

		if (Math.signum(dx) == Math.signum(dy))
			return Math.max(Math.abs(dx), Math.abs(dy));
		return Math.abs(dx) + Math.abs(dy);
	}

	private static int floor2(int x) {
		return x >= 0 ? x >> 1 : x - 1 >> 1;
	}

	private static int ceil2(int x) {
		return x >= 0 ? x + 1 >> 1 : x >> 1;
	}
}
