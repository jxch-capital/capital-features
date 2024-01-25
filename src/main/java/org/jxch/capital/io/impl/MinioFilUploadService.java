package org.jxch.capital.io.impl;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.FileUploadService;
import org.jxch.capital.io.dto.FileMetaData;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
public class MinioFilUploadService implements FileUploadService {
    private final static String META_SUFFIX = "X-Amz-Meta-";
    private final MinioClient minioClient;

    public Map<String, String> toMinioMetaData(@NotNull Map<String, String> metaData) {
        return metaData.entrySet().stream().collect(Collectors.toMap(entry -> META_SUFFIX + entry.getKey(), Map.Entry::getValue));
    }

    @Override
    @SneakyThrows
    public String getFullPath(String path, String name) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(path).object(name).method(Method.GET).build());
    }

    @Override
    @SneakyThrows
    public String createPathIfNotExist(String path) {
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(path).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(path).build());
        }
        return path;
    }

    @Override
    @SneakyThrows
    public String upload(String path, @NotNull MultipartFile file, @NotNull FileMetaData metaData) {
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
        return StreamSupport.stream(minioClient.listObjects(ListObjectsArgs.builder().bucket(createPathIfNotExist(path)).recursive(true).build()).spliterator(), false)
                .map(itemResult -> itemResultToFileMetaData(itemResult, path)).toList();
    }

    @Override
    @SneakyThrows
    public String updateFileMetaData(String path, String oldName, @NotNull FileMetaData metaData) {
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
        StatObjectResponse objectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(path).object(name).build());
        return FileMetaData.builder().fileName(name).metaData(objectResponse.userMetadata()).build();
    }

    @Override
    @SneakyThrows
    public void delFile(String path, String name) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(path).object(name).build());
    }

}
