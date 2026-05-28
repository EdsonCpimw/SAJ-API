package com.saj.api.shared.utils;

public class StringUtils {

    private StringUtils() {}

    public static String removeFormatting(String value) {
        return value == null ? null : value.replaceAll("\\D", "");
    }
}
