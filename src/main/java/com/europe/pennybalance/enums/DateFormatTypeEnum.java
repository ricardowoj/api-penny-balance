package com.europe.pennybalance.enums;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public enum DateFormatTypeEnum {

    ISO("yyyy-MM-dd"), // Example: 2024-05-22
    US("MM/dd/yyyy"), // Example: 05/22/2024
    EUROPEAN_DATE("dd/MM/yyyy"), // Example: 22/05/2024
    DD_MMM_YYYY("dd MMM yyyy"), // Example: 22 May 2024
    DD_MMM("dd MMM"), // Example: 22 May
    YYYY("yyyy"), // Example: 2024
    RFC_1123("EEE, dd MMM yyyy HH:mm:ss z"); // Example: Wed, 22 May 2024 14:30:00 GMT

    private final String pattern;
    private final DateTimeFormatter formatter;

    DateFormatTypeEnum(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }
}
