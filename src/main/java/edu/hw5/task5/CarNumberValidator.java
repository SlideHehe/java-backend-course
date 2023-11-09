package edu.hw5.task5;

import java.util.Objects;

public class CarNumberValidator {
    private static final String ALLOWED_CHARS = "АВЕКМНОРСТУХ";

    private CarNumberValidator() {
    }

    public static boolean isCarNumberValid(String number) {
        Objects.requireNonNull(number);

        return number.matches("^[" + ALLOWED_CHARS + "]\\d{3}[" + ALLOWED_CHARS + "]{2}\\d{2,3}$");
    }
}
