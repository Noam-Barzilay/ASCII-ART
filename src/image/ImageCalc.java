package image;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for performing calculations and transformations on images.
 */
public class ImageCalc {
    private final int MAX_RGB = 255;

    /**
     * Fills the image with white pixels to make its dimensions power of 2.
     *
     * @param image The original image.
     * @return A new image with power-of-2 dimensions, padded with white pixels.
     */
    public Image imageFill(Image image){
        /** filling the image with white pixels, so it becomes power of 2's dimensioned **/
        // get the closest power of 2 using log
        int height = (int) (Math.pow(2, Math.ceil(Math.log(image.getHeight()) / Math.log(2))));
        int width = (int) (Math.pow(2, Math.ceil(Math.log(image.getWidth()) / Math.log(2))));
        // create new array
        Color[][] newPixelsArr = new Color[height][width];
        // get the two halves of height and width
        int heightHalf = (height - image.getHeight()) / 2;
        int widthHalf = (width - image.getWidth()) / 2;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // if we out of the original borders, we pad with white pixels
                if (i<heightHalf || i >= (height - heightHalf) || j<widthHalf || j >= (width - widthHalf)){
                    newPixelsArr[i][j] = new Color(255, 255, 255);
                }
                else{  // we need it to stay the same as it was before (minding the shift)
                    newPixelsArr[i][j] = image.getPixel(i - heightHalf, j - widthHalf);
                }
            }
        }
        return new Image(newPixelsArr, width, height);
    }

    /**
     * Divides the given image into sub-images based on the specified resolution.
     *
     * @param image      The original image.
     * @param resolution The number of sub-images per dimension.
     * @return A list of sub-images.
     */
    public List<Image> getSubImages(Image image ,int resolution) {
        List<Image> subImages = new ArrayList<>();
        // Calculate the width and height of each sub-image
        int subImageWidth = image.getWidth() / resolution;
        int subImageHeight = image.getHeight() / resolution;
        // Iterate through the sub-images
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                // Calculate the starting pixel coordinates for each sub-image
                int startY = j * subImageWidth;
                int startX = i * subImageHeight;
                // Create a 2D array for the sub-image's pixel colors
                Color[][] subImagePixels = new Color[subImageHeight][subImageWidth];
                // Copy pixel data from the original image to the sub-image
                for (int x = 0; x < subImageHeight; x++) {
                    for (int y = 0; y < subImageWidth; y++) {
                        subImagePixels[x][y] = image.getPixel(startX + x, startY + y);
                    }
                }
                // Create a new Image instance for the sub-image and add it to the list
                subImages.add(new Image(subImagePixels, subImageWidth, subImageHeight));
            }
        }
        return subImages;
    }


    /**
     * Calculates the average brightness of the given image.
     *
     * @param image The original image.
     * @return The average brightness value normalized between 0 and 1.
     */
    public double getImageBrightness(Image image){
        double greysSum = 0;
        int pixelsCount = 0;
        // go over the image, calculate the grey value
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color curPixel = image.getPixel(i, j);
                double greyPixel = curPixel.getRed() * 0.2126 + curPixel.getGreen() * 0.7152
                        + curPixel.getBlue() * 0.0722;
                greysSum += greyPixel;
                pixelsCount++;
            }
        }
        return greysSum / (pixelsCount * MAX_RGB);
    }

}
