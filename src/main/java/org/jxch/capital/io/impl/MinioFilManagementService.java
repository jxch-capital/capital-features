package org.jxch.capital.io.impl;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.config.IOConfig;
import org.jxch.capital.io.FileManagementService;
import org.jxch.capital.io.dto.FileMetaData;
import org.jxch.capital.utils.FileU;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class MinioFilManagementService implements FileManagementService {
    private final static String META_SUFFIX = "X-Amz-Meta-";
    private final MinioClient minioClient;
    private final IOConfig ioConfig;


    public String getNamespacePath(@NotNull String path) {
        return ioConfig.getNamespace() + path.replaceFirst(ioConfig.getNamespace(), "");
    }

    public Map<String, String> toMinioMetaData(@NotNull Map<String, String> metaData) {
        return metaData.entrySet().stream().collect(Collectors.toMap(entry -> META_SUFFIX + entry.getKey(), Map.Entry::getValue));
    }

    @Override
    @SneakyThrows
    public String getFullPath(String path, String name) {
        path = getNamespacePath(path);
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(path).object(name).method(Method.GET).build());
    }

    @Override
    @SneakyThrows
    public String createPathIfNotExist(String path) {
        path = getNamespacePath(path);
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(path).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(path).build());
        }
        return path;
    }

    @Override
    @SneakyThrows
    public String upload(String path, @NotNull MultipartFile file, @NotNull FileMetaData metaData) {
        path = getNamespacePath(path);
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(createPathIfNotExist(path))
                .object(file.getOriginalFilename())
                .stream(file.getInputStream(), file.getSize(), -1) // 设置输入流和大小
                .contentType(file.getContentType()) // 设置内容类型
                .headers(toMinioMetaData(metaData.getMetaData()))
                .build();
        log.warn("由于未知原因，必须在此休眠等待10s才能上传成功");
        Thread.sleep(10000);
        minioClient.putObject(putObjectArgs);
        return getFullPath(path, file.getOriginalFilename());
    }

    @SneakyThrows
    public FileMetaData itemResultToFileMetaData(@NotNull Result<Item> itemResult, String bucketName) {
        return getFileMetaData(bucketName, itemResult.get().objectName());
    }

    @Override
    @SneakyThrows
    public List<FileMetaData> allFileMetaData(String path) {
        return StreamSupport.stream(minioClient.listObjects(ListObjectsArgs.builder().bucket(createPathIfNotExist(getNamespacePath(path))).recursive(true).build()).spliterator(), false)
                .map(itemResult -> itemResultToFileMetaData(itemResult, getNamespacePath(path))).toList();
    }

    @Override
    @SneakyThrows
    public String updateFileMetaData(String path, String oldName, @NotNull FileMetaData metaData) {
        path = getNamespacePath(path);
        if (!Objects.equals(metaData.getFileName(), oldName)) {
            CopyObjectArgs args = CopyObjectArgs.builder()
                    .bucket(path)
                    .object(metaData.getFileName())
                    .source(CopySource.builder().bucket(path).object(oldName).build())
                    .headers(toMinioMetaData(metaData.getMetaData()))  // 添加新的元数据
                    .build();
            minioClient.copyObject(args);
            delFile(path, oldName);
        } else {
            UUID uuid = UUID.randomUUID();
            CopyObjectArgs args = CopyObjectArgs.builder()
                    .bucket(path)
                    .object(metaData.getFileName() + uuid)
                    .source(CopySource.builder().bucket(path).object(oldName).build())
                    .headers(toMinioMetaData(metaData.getMetaData()))  // 添加新的元数据
                    .build();
            minioClient.copyObject(args);
            delFile(path, oldName);
            args = CopyObjectArgs.builder()
                    .bucket(path)
                    .object(metaData.getFileName())
                    .source(CopySource.builder().bucket(path).object(metaData.getFileName() + uuid).build())
                    .headers(toMinioMetaData(metaData.getMetaData()))  // 添加新的元数据
                    .build();
            minioClient.copyObject(args);
            delFile(path, metaData.getFileName() + uuid);
        }

        return getFullPath(path, metaData.getFileName());
    }

    @Override
    @SneakyThrows
    public FileMetaData getFileMetaData(String path, String name) {
        path = getNamespacePath(path);
        StatObjectResponse objectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(path).object(name).build());
        return FileMetaData.builder().fileName(name).metaData(objectResponse.userMetadata()).build();
    }

    @Override
    @SneakyThrows
    public void delFile(String path, String name) {
        path = getNamespacePath(path);
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(path).object(name).build());
    }

    @Override
    @SneakyThrows
    public File getFile(String path, String name, boolean refreshLocal) {
        path = getNamespacePath(path);
        Path dir = Path.of(ioConfig.getMinio().getLocalPath()).resolve(path);
        FileU.mkdirIfNotExists(dir.toAbsolutePath().toString());
        File file = dir.resolve(name).toFile();

        if (file.exists() && !refreshLocal) {
            log.debug("本地文件已存在，无需从Minio下载：{}", file.toPath());
            return file;
        } else if (file.exists() && refreshLocal) {
            file.deleteOnExit();
        }

        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(path).object(name).build())) {
            Files.copy(stream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            log.debug("文件从Minio下载成功，保存到: {}", file.toPath());
            return file;
        }
    }

    @Override
    @SneakyThrows
    public boolean hasFile(String path, String name) {
        path = getNamespacePath(path);
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(path).object(name).build());
            return true;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                return false;
            }
            throw e;
        }
    }

}
