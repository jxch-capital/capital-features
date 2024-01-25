package org.jxch.capital.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class FileU {

    public static void mkdir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("创建成功：{}", path);
            } else {
                log.error("创建失败：{}", path);
            }
        }
    }

}
