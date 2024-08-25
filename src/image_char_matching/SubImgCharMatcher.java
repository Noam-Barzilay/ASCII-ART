package image_char_matching;

import ascii_art.Shell;

import java.util.*;

/**
 * A class for matching characters based on image brightness and managing a set of characters.
 */
public class SubImgCharMatcher {
    private TreeMap<Character, Double> charMapSet;

    /**
     * Constructs a SubImgCharMatcher with an initial set of characters and their brightness levels.
     *
     * @param charset An array of characters to initialize the matcher.
     */
    public SubImgCharMatcher(char[] charset) {

        charMapSet = new TreeMap<>();
        for (char c : charset) {
            charMapSet.put(c, calcCharBrightness(c));
        }
        calculateAllChar();
    }

    /**
     * Gets the character with the closest brightness match to the given brightness level.
     *
     * @param brightness The target brightness level.
     * @return The character with the closest brightness match.
     */
    public char getCharByImageBrightness(double brightness) {
        char minChar = Collections.min(charMapSet.keySet());
        for (Character key : charMapSet.keySet()) {
            if (Math.abs(charMapSet.get(key) - brightness) < Math.abs(charMapSet.get(minChar) - brightness)){
                minChar = key;
            }
        }
        return minChar;
    }

    /**
     * Adds a character to the matcher with its calculated brightness level.
     *
     * @param c The character to add.
     */
    public void addChar(char c) {
        if (Shell.charBrightnessContains(c)){
            charMapSet.put(c, Shell.getCharBrightness(c));
        }
        else {
            charMapSet.put(c, calcCharBrightness(c));
            Shell.setCharBrightness(c, calcCharBrightness(c));
        }
        calculateAllChar();
    }

    /**
     * Removes a character from the matcher.
     *
     * @param c The character to remove.
     */
    public void removeChar(char c) {
        charMapSet.remove(c);
        calculateAllChar();
    }

    /**
     * Returns charMapSet keys set.
     *
     * @return the keys set of charMapSet.
     */
    public Set<Character> getKeysSet(){
        return charMapSet.keySet();
    }
    /**
     * Returns charMapSet size.
     *
     * @return the size of charMapSet.
     */
    public int getSetSize(){
        return charMapSet.size();
    }

    /**
     * Recalculates the normalized brightness levels for all characters in the set.
     */
    private void calculateAllChar() {
        if (charMapSet.size() > 1){
            double min = Collections.min(charMapSet.values());
            double max = Collections.max(charMapSet.values());
            for (Map.Entry<Character, Double> entry : charMapSet.entrySet()) {
                charMapSet.put(entry.getKey(), newCalcCharBrightness(entry.getValue(), min, max));
            }
        }
    }

    /**
     * Normalizes the brightness level of a character within a specified range.
     *
     * @param charBrightness The original brightness level of the character.
     * @param minBrightness  The minimum brightness level in the set.
     * @param maxBrightness  The maximum brightness level in the set.
     * @return The normalized brightness level.
     */
    private double newCalcCharBrightness(double charBrightness, double minBrightness, double maxBrightness) {
        return (charBrightness - minBrightness) / (maxBrightness - minBrightness);
    }

    /**
     * Calculates the brightness level of a character based on
     * the count of 'true' cells in its binary representation.
     *
     * @param c The character to calculate brightness for.
     * @return The calculated brightness level.
     */
    private double calcCharBrightness(char c) {
        boolean[][] charBoolArr = CharConverter.convertToBoolArray(c);
        return (double) countWhiteCells(charBoolArr) / (charBoolArr.length * charBoolArr[0].length);
    }

    /**
     * Counts the number of 'true' cells in a boolean array.
     *
     * @param charBoolArr The boolean array to count 'true' cells in.
     * @return The count of 'true' cells.
     */
    private int countWhiteCells(boolean[][] charBoolArr) {
        int whiteCellsCount = 0;
        for (boolean[] row : charBoolArr) {
            for (boolean cell : row) {
                if (cell) {
                    whiteCellsCount++;
                }
            }
        }
        return whiteCellsCount;
    }
}
