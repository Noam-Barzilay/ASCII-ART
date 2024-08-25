package image_char_matching;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Converts characters to a binary "image" represented as a 2D array of booleans.
 */
public class CharConverter {
    // the x offset factor
    private static final double X_OFFSET_FACTOR = 0.2;
    // the y offset factor
    private static final double Y_OFFSET_FACTOR = 0.75;
    // the font to be used in the conversion
    private static final String FONT_NAME = "Courier New";
    /**
     * the default pixel resolution
     */
    public static final int DEFAULT_PIXEL_RESOLUTION = 16;
    /**
     * Renders a given character, according to how it looks in the font specified in the
     * constructor, to a square black&white image (2D array of booleans),
     * whose dimension in pixels is specified.
     * @param c The character to convert.
     * @return A boolean array representing the binary image of the character.
     */
    public static boolean[][] convertToBoolArray(char c) {
        BufferedImage img = getBufferedImage(c, FONT_NAME, DEFAULT_PIXEL_RESOLUTION);
        boolean[][] matrix = new boolean[DEFAULT_PIXEL_RESOLUTION][DEFAULT_PIXEL_RESOLUTION];
        for(int y = 0 ; y < DEFAULT_PIXEL_RESOLUTION ; y++) {
            for(int x = 0 ; x < DEFAULT_PIXEL_RESOLUTION ; x++) {
                matrix[y][x] = img.getRGB(x, y) == 0; //is the color black
            }
        }
        return matrix;
    }

    private static BufferedImage getBufferedImage(char c, String fontName, int pixelsPerRow) {
        String charStr = Character.toString(c);
        Font font = new Font(fontName, Font.PLAIN, pixelsPerRow);
        BufferedImage img = new BufferedImage(pixelsPerRow, pixelsPerRow, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setFont(font);
        int xOffset = (int)Math.round(pixelsPerRow *X_OFFSET_FACTOR);
        int yOffset = (int)Math.round(pixelsPerRow *Y_OFFSET_FACTOR);
        g.drawString(charStr, xOffset, yOffset);
        return img;
    }

}
