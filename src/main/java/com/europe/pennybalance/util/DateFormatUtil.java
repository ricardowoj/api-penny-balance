package com.europe.pennybalance.util;

import com.europe.pennybalance.enums.DateFormatTypeEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.EnumMap;
import java.util.Locale;

public class DateFormatUtil {

    private static final EnumMap<DateFormatTypeEnum, DateTimeFormatter> formatters = new EnumMap<>(DateFormatTypeEnum.class);

    static {
        for (DateFormatTypeEnum formatType : DateFormatTypeEnum.values()) {
            formatters.put(formatType, DateTimeFormatter.ofPattern(formatType.getPattern(), Locale.ENGLISH));
        }
    }

    public static boolean isValid(DateFormatTypeEnum formatType, String dateStr) {
        DateTimeFormatter formatter = formatters.get(formatType);
        if (formatter == null) return false;
        dateStr = dateStr.trim();
        return isValidDate(formatter, dateStr);
    }

    private static boolean isValidDate(DateTimeFormatter formatter, String dateStr) {
        try {
            LocalDate.parse(dateStr, formatter);
            return true;
        } catch (DateTimeParseException e) {
            try {
                formatter.parse(dateStr);
                return true;
            } catch (DateTimeParseException ex) {
                return false;
            }
        }
    }

    public static LocalDate parse(DateFormatTypeEnum formatType, String dateStr) {
        DateTimeFormatter formatter = formatters.get(formatType);
        if (formatter == null) throw new IllegalArgumentException("Invalid format key");
        return LocalDate.parse(dateStr, formatter);
    }
}