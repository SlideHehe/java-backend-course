package edu.hw1;

import java.util.Objects;

public class Task1 {
    private Task1() {
    }

    private static final int SECONDS_IN_MINUTE = 60;
    public static final int MAX_MINUTES = (Integer.MAX_VALUE - SECONDS_IN_MINUTE) / SECONDS_IN_MINUTE;

    public static int minutesToSeconds(String time) {
        if (Objects.isNull(time)) {
            return -1;
        }

        String[] timeNumbers = time.split(":");

        int minutes;
        int seconds;

        try {
            minutes = Integer.parseInt(timeNumbers[0]);
            seconds = Integer.parseInt(timeNumbers[1]);
        } catch (NumberFormatException | IndexOutOfBoundsException exception) {
            return -1;
        }

        if (seconds >= SECONDS_IN_MINUTE || seconds < 0 || minutes < 0 || minutes > MAX_MINUTES) {
            return -1;
        }

        return minutes * SECONDS_IN_MINUTE + seconds;
    }

}
