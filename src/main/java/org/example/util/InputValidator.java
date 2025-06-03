package org.example.util;

import java.util.OptionalInt;

public interface InputValidator {
    boolean isValidName(String input);
    OptionalInt parseIntInRange(String input, int min, int max);
}
