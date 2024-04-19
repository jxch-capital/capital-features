package org.jxch.capital.utils;

import org.jetbrains.annotations.NotNull;

public class NumberU {

    public static long fillLong(@NotNull String number, int size) {
        return Long.parseLong(number + "0".repeat(size - number.length()));
    }

    public static long incFillLong(@NotNull String number, int size) {
        return Long.parseLong((Long.parseLong(number) + 1) + "0".repeat(size - number.length()));
    }

}
