package org.example.util;

import java.util.OptionalInt;

public class InputValidatorIMPL implements InputValidator {
    @Override
    public boolean isValidName(String input) {
        if (input == null || input.trim().isEmpty()) return false;
        char[] chars = input.toCharArray();
        if (!startAndEndWithLetter(chars)) return false;
        return hasValidCharactersInside(chars);
    }

    @Override
    public OptionalInt parseIntInRange(String input, int min, int max) {
        try {
            int amount = Integer.parseInt(input);
            if (amount >= min && amount <= max) {
                return OptionalInt.of(amount);
            }
        } catch (NumberFormatException e) {}
        return OptionalInt.empty();
    }

    private boolean startAndEndWithLetter(char[] chars) {
        return Character.isLetter(chars[0]) && Character.isLetter(chars[chars.length - 1]);
    }

    private boolean hasValidCharactersInside(char[] chars) {
        int notCharInARow = 0;
        for (char c : chars) {
            if (Character.isLetter(c)) {
                notCharInARow = 0;
            } else if (c == ' ' || c == '-' || c == '\'') {
                notCharInARow++;
                if (notCharInARow >= 2) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}
