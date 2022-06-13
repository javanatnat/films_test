package ru.javanatnat.javafxexample;

import javafx.scene.control.TextFormatter;

import java.time.LocalDateTime;

public class TextFormat {
    private static final int MIN_YEAR_FILM = 1895;
    private static final int MAX_YEAR_FILM = LocalDateTime.now().getYear();

    static TextFormatter<?> getYearTextFormatter() {
        return new TextFormatter<>(c -> {
            String newValue = c.getControlNewText();
            int year = getIntValue(newValue);
            if (year < MIN_YEAR_FILM || year > MAX_YEAR_FILM) {
                return null;
            }
            return c;
        });
    }

    static TextFormatter<?> getBudgetTextFormatter() {
        return new TextFormatter<>(c -> {
            String newValue = c.getControlNewText();
            if (getLongValue(newValue) <= 0) {
                return null;
            }
            return c;
        });
    }

    private static int getIntValue(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static long getLongValue(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
