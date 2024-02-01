package org.jxch.capital.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.ZipFile;

@Slf4j
public class ZipU {

    public static boolean isZipFile(File file) {
        try (ZipFile ignored = new ZipFile(file)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @SneakyThrows
    public static boolean isZipFileByMagic(File file) {
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            int magic = raf.readInt();
            return magic == 0x504B0304 || magic == 0x504B0506 || magic == 0x504B0708;
        }
    }

    @NotNull
    @Contract("_ -> new")
    public static File unZip(File zipFile) {
        return unZip(zipFile, zipFile.getParentFile());
    }

    @NotNull
    public static File unZip(File zipFile, @NotNull File destDir) {
        return unZip(zipFile, destDir, false);
    }

    @NotNull
    @SneakyThrows
    public static File unZip(File zipFile, @NotNull File destDir, boolean coverage) {
        FileU.mkdirIfNotExists(destDir);
        File firstUnzipped = null;

        try (InputStream fi = Files.newInputStream(zipFile.toPath());
             InputStream bi = new BufferedInputStream(fi);
             ArchiveInputStream<ZipArchiveEntry> i = new ZipArchiveInputStream(bi)) {

            ArchiveEntry entry;
            while ((entry = i.getNextEntry()) != null) {
                if (!i.canReadEntryData(entry)) {
                    continue;
                }

                File f = new File(destDir, entry.getName());
                if (Objects.isNull(firstUnzipped)) {
                    firstUnzipped = f;
                    if (firstUnzipped.exists() && !coverage) {
                        log.debug("目录已存在，无需解压");
                        if (firstUnzipped.getName().equals("assets")) {
                            return firstUnzipped.toPath().getParent().toFile();
                        } else {
                            return firstUnzipped;
                        }
                    }
                }

                if (entry.isDirectory()) {
                    if (!f.isDirectory() && !f.mkdirs()) {
                        throw new IOException("failed to create directory " + f);
                    }
                } else {
                    File parent = f.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("failed to create directory " + parent);
                    }
                    try (OutputStream o = Files.newOutputStream(f.toPath(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                         BufferedOutputStream bos = new BufferedOutputStream(o)) {
                        IOUtils.copy(i, bos);
                    }
                }
            }
        }

       return Optional.ofNullable(firstUnzipped).orElseThrow(() -> new IllegalArgumentException("解压失败，请查看解压文件和目标目录是否正确"));
    }

}
