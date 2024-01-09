package org.jxch.capital.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Controllers {

    @NotNull
    @Contract(pure = true)
    public static String redirect(String path) {
        return "redirect:" + path;
    }

}
