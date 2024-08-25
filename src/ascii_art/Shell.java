package ascii_art;

import Exceptions.*;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.*;

/**
 * The Shell class represents a command-line shell for generating ASCII art from an image.
 */
public class Shell {
    // constants
    /**
     * Default path for the image file.
     */
    private String DEFAULT_IMAGE_PATH = "src/example_images/cat.jpeg";

    /**
     * Default character set for ASCII art.
     */
    private final char[] DEFAULT_CHARS_SET = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * Index after the "add" command in user input.
     */
    private final int INDEX_AFTER_ADD_COMMAND = 4;

    /**
     * Length of characters to be added.
     */
    private final int ADD_CHAR_LENGTH = 1;

    /**
     * Minimum ASCII value allowed.
     */
    private final int MIN_ASCII_VAL = 32;

    /**
     * Maximum ASCII value allowed.
     */
    private final int MAX_ASCII_VAL = 126;

    /**
     * ASCII value for space character.
     */
    private final int SPACE_ASCII = 32;

    /**
     * Error message for invalid "add" command format.
     */
    private final String INVALID_ADD_FORMAT_ERROR = "Did not add due to incorrect format.";

    /**
     * Error message for invalid "remove" command format.
     */
    private final String INVALID_REMOVE_FORMAT_ERROR = "Did not remove due to incorrect format.";

    /**
     * Index after the "remove" command in user input.
     */
    private final int INDEX_AFTER_REMOVE_COMMAND = 7;

    /**
     * Index after the "res" command in user input.
     */
    private final int INDEX_AFTER_RES_COMMAND = 4;

    /**
     * Message indicating the current number of characters when changing resolution.
     */
    private final String CUR_NUM_OF_CHARS = "Resolution set to ";

    /**
     * Error message for exceeding resolution boundaries.
     */
    private final String EXCEED_VALUE_ERROR = "Did not change resolution due to exceeding boundaries.";

    /**
     * Error message for invalid "res" command format.
     */
    private final String INVALID_RES_FORMAT_ERROR = "Did not change resolution due to incorrect format.";

    /**
     * Index after the "image" command in user input.
     */
    private final int INDEX_AFTER_IMAGE_COMMAND = 6;

    /**
     * Error message for problems with image file during execution.
     */
    private final String IO_ERROR = "Did not execute due to problem with image file.";

    /**
     * Index after the "output" command in user input.
     */
    private final int INDEX_AFTER_OUTPUT_COMMAND = 7;

    /**
     * Error message for invalid "output" command format.
     */
    private final String INVALID_OUTPUT_FORMAT_ERROR =
            "Did not change output method due to incorrect format.";

    /**
     * Error message for empty character set during execution.
     */
    private final String EMPTY_CHAR_SET_ERROR = "Did not execute. Charset is empty.";

    /**
     * Error message for an invalid command.
     */
    private final String INVALID_COMMAND_ERROR = "Did not execute due to incorrect command.";

    // fields
    /**
     * Current resolution for ASCII art generation.
     */
    private int resolution = 128;

    /**
     * The Image object representing the source image.
     */
    private Image image;

    /**
     * Path of the current image file.
     */
    private String imagePath = DEFAULT_IMAGE_PATH;

    /**
     * Output method for displaying ASCII art (default: ConsoleAsciiOutput).
     */
    private AsciiOutput asciiOutput = new ConsoleAsciiOutput();
    /**
     * subImgCharMatcher object to handle the data structure (chars set).
     */
    private SubImgCharMatcher subImgCharMatcher = new SubImgCharMatcher(DEFAULT_CHARS_SET);
    /**
     * HashMap object to store pairs of image and its corresponding (list of) brightness values.
     */
    private static HashMap<Image, List<Double>> imgBrightnessMap = new HashMap<>();

    /**
     * HashMap object to store pairs of character and its corresponding brightness values.
     */
    private static HashMap<Character, Double> charBrightnessMap = new HashMap<>();

    /**
     * Initializes the character set and image for ASCII art generation.
     */
    public void run() throws Exception {
        initializeCharsSet(DEFAULT_CHARS_SET);
        image = new Image(DEFAULT_IMAGE_PATH);
        // Main command loop
        while (true) {
            try {
                // Get user input
                System.out.print(">>> ");
                String userInput = KeyboardInput.readLine();
                // Check various commands
                if (userInput.isEmpty()) {}  // If user entered enter, continue loop
                else if (userInput.equalsIgnoreCase("exit")) {
                    // Exit the program
                    break;
                } else if (userInput.equalsIgnoreCase("chars")) {
                    // Display the set of characters
                    handleChars();
                } else if (userInput.toLowerCase().contains("add")) {
                    // Add characters to the set
                    handleAdd(userInput);
                } else if (userInput.toLowerCase().contains("remove")) {
                    // Remove characters from the set
                    handleRemove(userInput);
                } else if (userInput.toLowerCase().contains("res")) {
                    // Change the image's resolution
                    handleResolution(userInput);
                } else if (userInput.toLowerCase().contains("image")) {
                    // Change the image file
                    handleImage(userInput);
                } else if (userInput.toLowerCase().contains("output")) {
                    // Change the output source
                    handleOutput(userInput);
                } else if (userInput.equalsIgnoreCase("asciiart")) {
                    // Run the ASCII art algorithm
                    handleAsciiArt();
                } else {
                    // Invalid command
                    throw new InvalidCommandException(INVALID_COMMAND_ERROR);
                }
            } catch (EmptySetException emptySetException) {
                System.out.println(EMPTY_CHAR_SET_ERROR);
            } catch (ExceedingValueException exceedingValueException) {
                System.out.println(EXCEED_VALUE_ERROR);
            } catch (IncorrectAddFormatException incorrectFormatException) {
                System.out.println(INVALID_ADD_FORMAT_ERROR);
            } catch (IncorrectRemoveFormatException incorrectRemoveFormatException) {
                System.out.println(INVALID_REMOVE_FORMAT_ERROR);
            } catch (IncorrectResFormatException incorrectResFormatException) {
                System.out.println(INVALID_RES_FORMAT_ERROR);
            } catch (IncorrectOutputFormatException incorrectOutputFormatException) {
                System.out.println(INVALID_OUTPUT_FORMAT_ERROR);
            } catch (IOException ioException) {
                System.out.println(IO_ERROR);
            }
            catch (InvalidCommandException invalidCommandException) {
                System.out.println(INVALID_COMMAND_ERROR);
            }
        }
    }

    /**
     * The main method to start the ASCII art shell.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) throws Exception {
        Shell shell = new Shell();
        shell.run();
    }

    /**
     * Gets the brightness of the given image.
     *
     * @param image The image to return its brightness.
     * @return The brightness.
     */
    public static List<Double> getImgBrightness(Image image){
        return imgBrightnessMap.get(image);
    }

    /**
     * Sets the brightness of the given image.
     *
     * @param image The image to set its brightness.
     * @param brightness the brightness of the image.
     */
    public static void setImgBrightness(Image image, List<Double> brightness){
        imgBrightnessMap.put(image, brightness);
    }

    /**
     * checks if the imgBrightnessMap contains the given image.
     *
     * @param image The image to check if it is contained in imgBrightnessMap.
     * @return if the image is in imgBrightnessMap or not.
     */
    public static boolean ImgBrightnessContains(Image image){
        return imgBrightnessMap.containsKey(image);
    }

    /**
     * Gets the brightness of the given character.
     *
     * @param ch The character to return its brightness.
     * @return The brightness.
     */
    public static double getCharBrightness(char ch){
        return charBrightnessMap.get(ch);
    }

    /**
     * Sets the brightness of the given character.
     *
     * @param ch The character to set its brightness.
     * @param brightness the brightness of the character.
     */
    public static void setCharBrightness(char ch, double brightness){
        charBrightnessMap.put(ch, brightness);
    }

    /**
     * checks if the charBrightnessMap contains the given character.
     *
     * @param ch The character to check if it is contained in charBrightnessMap.
     * @return if the image is in charBrightnessMap or not.
     */
    public static boolean charBrightnessContains(char ch){
        return charBrightnessMap.containsKey(ch);
    }

    /**
     * Generates and displays ASCII art using the current settings.
     *
     * @throws EmptySetException If the character set is empty.
     * @throws IOException       If there is an issue with the image file.
     */
    private void handleAsciiArt() throws EmptySetException, IOException {
        // Transform char set TreeMap to an array of chars
        char[] charSet = new char[subImgCharMatcher.getSetSize()];
        int i = 0;
        for (char chKey : subImgCharMatcher.getKeysSet()) {
            charSet[i] = chKey;
            i++;
        }
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(imagePath, resolution, charSet);
        if (charSet.length == 0) {
            throw new EmptySetException(EMPTY_CHAR_SET_ERROR);
        }
        asciiOutput.out(asciiArtAlgorithm.run());
    }

    /**
     * Changes the output source for displaying ASCII art.
     *
     * @param userInput The user input containing the output source command.
     * @throws IncorrectOutputFormatException If the output source command is in an incorrect format.
     */
    private void handleOutput(String userInput) throws IncorrectOutputFormatException {
        if (userInput.length() < INDEX_AFTER_OUTPUT_COMMAND){
            throw new IncorrectOutputFormatException(INVALID_OUTPUT_FORMAT_ERROR);
        }
        String specificCmd = userInput.substring(INDEX_AFTER_OUTPUT_COMMAND);
        if (specificCmd.equalsIgnoreCase("html")) {
            asciiOutput = new HtmlAsciiOutput("out.html", "Courier New");
        } else if (specificCmd.equalsIgnoreCase("console")) {
            asciiOutput = new ConsoleAsciiOutput();
        } else {
            throw new IncorrectOutputFormatException(INVALID_OUTPUT_FORMAT_ERROR);
        }
    }

    /**
     * Changes the image file used for ASCII art generation.
     *
     * @param userInput The user input containing the image file command.
     * @throws IOException If there is an issue with the new image file.
     */
    private void handleImage(String userInput) throws IOException {
        if (userInput.length() < INDEX_AFTER_IMAGE_COMMAND){
            throw new IOException(IO_ERROR);
        }
        String specificCmd = userInput.substring(INDEX_AFTER_IMAGE_COMMAND);
        try {
            image = new Image(specificCmd);
            imagePath = specificCmd;
        } catch (IOException ioException) {
            throw new IOException(IO_ERROR);
        }
    }

    /**
     * Changes the resolution for ASCII art generation.
     *
     * @param userInput The user input containing the resolution command.
     * @throws ExceedingValueException        If the new resolution exceeds boundaries.
     * @throws IncorrectResFormatException    If the resolution command is in an incorrect format.
     */
    private void handleResolution(String userInput) throws
            ExceedingValueException, IncorrectResFormatException {
        if (userInput.length() < INDEX_AFTER_RES_COMMAND){
            throw new IncorrectResFormatException(INVALID_RES_FORMAT_ERROR);
        }
        String specificCmd = userInput.substring(INDEX_AFTER_RES_COMMAND);
        if (specificCmd.equalsIgnoreCase("up")) {
            // Check if exceeds the max resolution
            if (resolution * 2 > image.getWidth()) {
                throw new ExceedingValueException(EXCEED_VALUE_ERROR);
            }
            resolution *= 2;
            System.out.println(CUR_NUM_OF_CHARS + resolution);
        } else if (specificCmd.equalsIgnoreCase("down")) {
            // Check if exceeds the min resolution
            if (resolution / 2 < Math.max(1, image.getWidth() / image.getHeight())) {
                throw new ExceedingValueException(EXCEED_VALUE_ERROR);
            }
            resolution /= 2;
            System.out.println(CUR_NUM_OF_CHARS + resolution);
        } else {
            throw new IncorrectResFormatException(INVALID_RES_FORMAT_ERROR);
        }
    }

    /**
     * Removes characters from the set based on user input.
     *
     * @param userInput The user input containing the remove command.
     * @throws IncorrectRemoveFormatException If the remove command is in an incorrect format.
     */
    private void handleRemove(String userInput) throws IncorrectRemoveFormatException {
        if (userInput.length() < INDEX_AFTER_REMOVE_COMMAND){
            throw new IncorrectRemoveFormatException(INVALID_REMOVE_FORMAT_ERROR);
        }
        String specificCmd = userInput.substring(INDEX_AFTER_REMOVE_COMMAND);
        // Remove single char from the set
        if (specificCmd.length() == ADD_CHAR_LENGTH) {
            char charToRemove = userInput.charAt(INDEX_AFTER_REMOVE_COMMAND);
            subImgCharMatcher.removeChar(charToRemove);
        } else if (specificCmd.equalsIgnoreCase("all")) {
            // Remove all possible characters from the set
            for (int i = MIN_ASCII_VAL; i < MAX_ASCII_VAL + 1; i++) {
                subImgCharMatcher.removeChar((char) i);
            }
        } else if (specificCmd.equalsIgnoreCase("space")) {
            // Remove the space character from the set
            subImgCharMatcher.removeChar((char) SPACE_ASCII);
        } else if (specificCmd.matches("\\b([^\\s])-([^\\s])\\b")) {
            // Remove all characters in a given range character1-character2
            char char1 = specificCmd.charAt(0);
            char char2 = specificCmd.charAt(2);
            for (int i = Math.min(char1, char2); i < Math.max(char1, char2) + 1; i++) {
                subImgCharMatcher.removeChar((char) i);
            }
        } else {
            // Throw custom informative error
            throw new IncorrectRemoveFormatException(INVALID_REMOVE_FORMAT_ERROR);
        }
    }

    /**
     * Adds characters to the set based on user input.
     *
     * @param userInput The user input containing the add command.
     * @throws IncorrectAddFormatException If the add command is in an incorrect format.
     */
    private void handleAdd(String userInput) throws IncorrectAddFormatException {
        if (userInput.length() < INDEX_AFTER_ADD_COMMAND){
            throw new IncorrectAddFormatException(INVALID_ADD_FORMAT_ERROR);
        }
        String specificCmd = userInput.substring(INDEX_AFTER_ADD_COMMAND);
        // Add single char to the set
        if (specificCmd.length() == ADD_CHAR_LENGTH) {
            char charToAdd = userInput.charAt(INDEX_AFTER_ADD_COMMAND);
            subImgCharMatcher.addChar(charToAdd);
        } else if (specificCmd.equalsIgnoreCase("all")) {
            // Add all possible characters to the set
            for (int i = MIN_ASCII_VAL; i < MAX_ASCII_VAL + 1; i++) {
                subImgCharMatcher.addChar((char) i);
            }
        } else if (specificCmd.equalsIgnoreCase("space")) {
            // Add the space character to the set
            subImgCharMatcher.addChar((char) SPACE_ASCII);
        } else if (specificCmd.matches("\\b([^\\s])-([^\\s])\\b")) {
            // Add all characters in a given range character1-character2
            char char1 = specificCmd.charAt(0);
            char char2 = specificCmd.charAt(2);
            for (int i = Math.min(char1, char2); i < Math.max(char1, char2) + 1; i++) {
                subImgCharMatcher.addChar((char) i);
            }
        } else {
            // Throw custom informative error
            throw new IncorrectAddFormatException(INVALID_ADD_FORMAT_ERROR);
        }
    }

    /**
     * Displays the set of characters.
     */
    private void handleChars() {
        for (char ch : subImgCharMatcher.getKeysSet()) {
            System.out.print(ch + " ");
        }
        System.out.println();
    }

    /**
     * Initializes the character set with default characters.
     *
     * @param defaultChars  The default characters to be added to the set.
     */
    private void initializeCharsSet(char[] defaultChars) {
        for (char ch : defaultChars) {
            subImgCharMatcher.addChar(ch);
        }
    }

}
