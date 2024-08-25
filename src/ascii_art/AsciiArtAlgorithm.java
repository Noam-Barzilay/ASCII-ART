/**
 * Package containing classes related to ASCII art generation.
 */
package ascii_art;

import image.Image;
import image.ImageCalc;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The `AsciiArtAlgorithm` class represents an algorithm for generating ASCII art from an image.
 * It converts an input image into a grid of characters based on brightness values.
 */
public class AsciiArtAlgorithm {
    private int resolution;
    private Image image;
    private ImageCalc imageCalc = new ImageCalc();
    private SubImgCharMatcher subImgCharMatcher;
    private List<Character> newCharArr = new ArrayList<>();
    private int newCharArrIdx = 0;

    /**
     * Constructor for `AsciiArtAlgorithm` class.
     *
     * @param imagePath  The file path to the input image.
     * @param resolution The desired resolution (number of characters) for the output ASCII art.
     * @param charSet    The character set to be used in the ASCII art.
     */
    public AsciiArtAlgorithm(String imagePath, int resolution, char[] charSet) throws IOException {
        this.resolution = resolution;
        image = new Image(imagePath);
        subImgCharMatcher = new SubImgCharMatcher(charSet);
    }

    /**
     * Executes the ASCII art generation algorithm.
     *
     * @return A 2D array of characters representing the generated ASCII art.
     */
    public char[][] run(){
        // Create the full image array by filling gaps in the input image
        Image filledImage = imageCalc.imageFill(image);

        // Result array to hold the generated ASCII art
        char[][] res = new char[resolution][resolution];

        // Divide the filled image into sub-images based on the desired resolution
        List<Image> subImagesList = imageCalc.getSubImages(filledImage, resolution);

        // Replace each sub-image with the closest character in terms of brightness
        handleBrightnessCalc(subImagesList);

        // Fill the result array with the generated characters
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                res[i][j] = newCharArr.get(newCharArrIdx);
                newCharArrIdx++;
            }
        }
        return res;
    }

    /**
     * This method handles the calculation of brightness for a list of sub-images and assigns
     * corresponding characters.
     * The brightness calculation is based on the image's pixel values.
     * If the global image brightness information is available in the Shell and matches the current image,
     * it retrieves the brightness array and assigns characters accordingly.
     * Otherwise, it calculates the brightness for each sub-image, assigns characters, and updates the global
     * brightness information.
     *
     * @param subImagesList A list of sub-images to calculate brightness for.
     */
    private void handleBrightnessCalc(List<Image> subImagesList) {
        // Check if the global image brightness information contains the current image
        if (Shell.ImgBrightnessContains(image)) {
            // If available, retrieve the brightness array from the Shell
            List<Double> brightnessesArr = Shell.getImgBrightness(image);

            // Assign characters based on the brightness array
            for (double brightness : brightnessesArr) {
                newCharArr.add(subImgCharMatcher.getCharByImageBrightness(brightness));
            }
        } else {
            // If brightness information is not available in Shell, calculate and update
            List<Double> brightnessesArr = new ArrayList<>();

            // Calculate brightness for each sub-image and assign characters
            for (Image subImage : subImagesList) {
                double subImgBrightness = imageCalc.getImageBrightness(subImage);
                newCharArr.add(subImgCharMatcher.getCharByImageBrightness(subImgBrightness));

                // Store the calculated brightness for each sub-image
                brightnessesArr.add(subImgBrightness);
            }

            // Update the global brightness information in the Shell
            Shell.setImgBrightness(image, brightnessesArr);
        }
    }

}
