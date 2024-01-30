package org.jxch.capital.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Controllers {

    @NotNull
    @Contract(pure = true)
    public static String redirect(String path) {
        return "redirect:" + path;
    }

    @NotNull
    public static String wsUrl(@NonNull HttpServletRequest request, String subPath) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String wsUrl = serverName + ":" + serverPort + contextPath + subPath;
        return "ws://" + wsUrl;
    }

    public static boolean isHttpRequestThread() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes instanceof ServletRequestAttributes;
    }

}
