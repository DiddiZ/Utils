package de.diddiz.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public final class ImageUtils
{
	/**
	 * Converts a {@link BufferedImage} of any type, to a {@link BufferedImage} of a specified type.
	 * 
	 * If the source image is of the same type as the target type, then the original image is returned, otherwise new image of the correct type is created and the content of the source image is copied into the new image.
	 * 
	 * @param sourceImage the image to be converted
	 * @param targetType the desired BufferedImage type
	 * 
	 * @return a BufferedImage of the specified target type.
	 */
	public static BufferedImage convertToType(BufferedImage sourceImage, int targetType) {
		// if the source image is already the target type, return the source image
		if (sourceImage.getType() == targetType)
			return sourceImage;

		// otherwise create a new image of the target type and draw the new image
		final BufferedImage image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), targetType);
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		return image;
	}

	/**
	 * Tries to get width and height from the image file.
	 * 
	 * @return {@code Dimension} or null
	 */
	public static Dimension getImageSize(File file) throws IOException {
		try (final ImageInputStream in = ImageIO.createImageInputStream(file)) {
			final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
			if (readers.hasNext()) {
				final ImageReader reader = readers.next();
				try {
					reader.setInput(in);
					return new Dimension(reader.getWidth(0), reader.getHeight(0));
				} finally {
					reader.dispose();
				}
			}
		}
		return null;
	}

	/**
	 * Creates a new resized BufferedImage with the supplied size for the longer edge
	 * 
	 * @param img BufferedImage to resize
	 * @param size Length of the longer edge
	 * @return A new resized image
	 */
	public static BufferedImage resize(BufferedImage img, int size) {
		final int width = img.getWidth(), height = img.getHeight();
		final int nWidth = width > height ? size : (int)((float)width / height * size);
		final int nHeight = height > width ? size : (int)((float)height / width * size);
		return resize(img, nWidth, nHeight);
	}

	/**
	 * Creates a new resized BufferedImage with the supplied dimensions
	 * 
	 * @return A new resized image
	 */
	public static BufferedImage resize(BufferedImage img, int newWidth, int newHeight) {
		final int w = img.getWidth();
		final int h = img.getHeight();
		final BufferedImage dimg = new BufferedImage(newWidth, newHeight, img.getType());
		final Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, w, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * Generates a BufferedImage from a ByteByffer. The ByteBuffer is assumed to be RGB formatted.
	 */
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
