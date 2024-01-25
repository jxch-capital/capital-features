package org.jxch.capital.io;

import org.jxch.capital.io.dto.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    String getFullPath(String path, String name);

    String createPathIfNotExist(String path);

    String upload(String path, MultipartFile file, FileMetaData metaData);

    List<FileMetaData> allFileMetaData(String path);

    String updateFileMetaData(String path, String oldName, FileMetaData metaData);

    FileMetaData getFileMetaData(String path, String name);

    void delFile(String path, String name);

}
