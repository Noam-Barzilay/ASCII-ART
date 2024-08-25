package ascii_art;

import java.util.Scanner;

/**
 * Singleton class for handling keyboard input.
 */
class KeyboardInput {
    // Singleton instance
    private static KeyboardInput keyboardInputObject = null;
    private Scanner scanner;

    // Private constructor to ensure a single instance
    private KeyboardInput() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Gets the singleton instance of KeyboardInput.
     *
     * @return The singleton instance of KeyboardInput.
     */
    public static KeyboardInput getObject() {
        if (KeyboardInput.keyboardInputObject == null) {
            KeyboardInput.keyboardInputObject = new KeyboardInput();
        }
        return KeyboardInput.keyboardInputObject;
    }

    /**
     * Reads a line of input from the keyboard and trims leading and trailing whitespaces.
     *
     * @return The input string after trimming.
     */
    public static String readLine() {
        return KeyboardInput.getObject().scanner.nextLine().trim();
    }
}
