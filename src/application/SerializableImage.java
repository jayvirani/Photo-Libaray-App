package application;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class SerializableImage implements Serializable {
	
	private static final long serialVersionUID = -1266447099158556616L;
	private int width, height;
	private int[][] pixels;
	
	/**
	 * Converts inputed image into a serializable image
	 * @param image the image to be converted
	 */
	public SerializableImage(Image image) {
		width = (int)image.getWidth();
		height = (int)image.getHeight();
		pixels = new int[width][height];
		
		PixelReader reader = image.getPixelReader();
		for (int currentWidth = 0; currentWidth < width; currentWidth++)
			for (int currentHeight = 0; currentHeight < height; currentHeight++)
				pixels[currentWidth][currentHeight] = reader.getArgb(currentWidth, currentHeight);
	}
	
	/**
	 * Deserializes serialized image back into regular image. 
	 * @return Image object
	 */
	public Image getImage() {
		WritableImage image = new WritableImage(width, height);
		
		PixelWriter w = image.getPixelWriter();
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				w.setArgb(i, j, pixels[i][j]);
		
		return image;
	}
	
	/**
	 * returns width of image
	 * @return the width of this image
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * returns height of image
	 * @return the height of this image
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * returns the 2D array representative of the image 
	 * @return a 2D int array representing the color values
	 */
	public int[][] getPixels() {
		return pixels;
	}
	
	/**
	 * checks if two images are the same
	 * @param image	
	 * @return true if images are the same. 
	 */
	public boolean equals(SerializableImage image) {
		if (width != image.getWidth() || height != image.getHeight())
			return false;
		
		for (int currentRow = 0; currentRow < width; currentRow++)
			for (int currentColumn = 0; currentColumn < height; currentColumn++)
				if (pixels[currentRow][currentColumn] != image.getPixels()[currentRow][currentColumn])
					return false;
		
		return true;
	}

	
}
