package de.diddiz.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class ImageUtils
{

	public static BufferedImage resize(BufferedImage img, int size) {
		final int width = img.getWidth(), height = img.getHeight();
		final int nWidth = width > height ? size : (int)((float)width / height * size);
		final int nHeight = height > width ? size : (int)((float)height / width * size);
		return resize(img, nWidth, nHeight);
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		final int w = img.getWidth();
		final int h = img.getHeight();
		final BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		final Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	public static BufferedImage toImage(ByteBuffer bb, int width, int height) {
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++) {
				final int i = (x + width * y) * 3;
				final int r = bb.get(i) & 0xFF;
				final int g = bb.get(i + 1) & 0xFF;
				final int b = bb.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), r << 16 | g << 8 | b);
			}
		return image;
	}
}
