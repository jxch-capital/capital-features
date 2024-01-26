package org.jxch.capital.utils;

import java.util.Objects;

public class ServiceU {
    private static final ThreadLocal<Boolean> IS_EXTERNAL_SERVICE = new ThreadLocal<>();

    public static void setExternalService() {
        IS_EXTERNAL_SERVICE.set(true);
    }

    public static boolean isExternalService() {
        return Objects.nonNull(IS_EXTERNAL_SERVICE.get()) && IS_EXTERNAL_SERVICE.get();
    }

    public static void removeExternalMark() {
        IS_EXTERNAL_SERVICE.remove();
    }

}
