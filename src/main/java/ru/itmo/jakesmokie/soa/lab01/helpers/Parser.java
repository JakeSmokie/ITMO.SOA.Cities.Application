package ru.itmo.jakesmokie.soa.lab01.helpers;

import ru.itmo.jakesmokie.soa.lab01.models.input.GovernmentDto;

import java.util.Optional;

public class Parser {
    public static Optional<Integer> parseInt(String toParse) {
        if (toParse == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Integer.parseInt(toParse));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Long> parseLong(String toParse) {
        if (toParse == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(Long.parseLong(toParse));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static <E extends Enum<E>> Optional<E> parseEnum(String text, Class<E> klass) {
        try {
            return Optional.of(Enum.valueOf(klass, text));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
