package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructs an Image object by reading an image from the specified file.
     *
     * @param filename The path to the image file.
     * @throws IOException If an error occurs while reading the image.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object with the provided pixel array, width, and height.
     *
     * @param pixelArray The 2D array representing the image pixels.
     * @param width      The width of the image.
     * @param height     The height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of the pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return The color of the specified pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a file with the specified file name.
     *
     * @param fileName The name of the file to save the image.
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if this image is equal to another object.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Check for reference equality
        if (obj == this) {
            return true;
        }

        // Check if the object is an instance of the Image class
        if (!(obj instanceof Image)) {
            return false;
        }

        // Cast the object to Image type for detailed comparison
        Image image = (Image) obj;

        // Compare dimensions of the images
        if (this.getHeight() != image.getHeight() || this.getWidth() != image.getWidth()) {
            return false;
        }

        // Compare pixel values of the images
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                if (this.getPixel(i, j) != image.getPixel(i, j)) {
                    return false;
                }
            }
        }
        // If all checks passed, the images are considered equal
        return true;
    }

    /**
     * Generates a hash code for the image.
     *
     * @return The hash code of the image.
     */
    @Override
    public int hashCode() {
        // Combine hash codes of height, width, and pixelArray using Objects.hash
        return Objects.hash(this.getHeight(), this.getWidth(), Arrays.deepHashCode(pixelArray));
    }

}
