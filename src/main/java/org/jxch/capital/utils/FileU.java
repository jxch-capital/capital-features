package org.jxch.capital.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

@Slf4j
public class FileU {

    public static void mkdirIfNotExists(String path) {
        mkdirIfNotExists(new File(path));
    }

    public static void mkdirIfNotExists(@NotNull File file) {
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("创建成功：{}", file.toPath());
            } else {
                log.error("创建失败：{}", file.toPath());
            }
        }
    }

    @NotNull
    public static Path newPathWithoutFileExtension(@NotNull File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex <= fileName.length() - 2 ) {
            return file.toPath().getParent().resolve(fileName.substring(0, dotIndex));
        }
        return file.toPath().getParent().resolve(fileName);
    }

}
