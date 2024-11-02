package com.europe.pennybalance.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class BigDecimalUtil {
    public static BigDecimal normalize(String amountStr) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.US);
            Number number = format.parse(amountStr.trim());
            return BigDecimal.valueOf(number.doubleValue());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid value format: " + amountStr, e);
        }
    }

    public static BigDecimal normalize(String amountStr, Locale locale) {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }
        try {
            NumberFormat format = NumberFormat.getInstance(locale);
            Number number = format.parse(amountStr);
            return BigDecimal.valueOf(number.doubleValue());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid value format: " + amountStr, e);
        }
    }

    public static String format(BigDecimal amount, String pattern) {
        if (amount == null) {
            throw new IllegalArgumentException("BigDecimal value cannot be null");
        }
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(amount);
    }
}