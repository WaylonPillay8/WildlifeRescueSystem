package com.mycompany.wildliferescuesystem;

/**
 * Reusable validation checks (kept separate so they can be unit tested).
 */
public class InputValidator {

    /** True if the text is null, empty or only spaces. */
    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    /** True if the number is greater than zero. */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /** True if the menu choice is between min and max (inclusive). */
    public static boolean isValidMenuChoice(int choice, int min, int max) {
        return choice >= min && choice <= max;
    }
}
