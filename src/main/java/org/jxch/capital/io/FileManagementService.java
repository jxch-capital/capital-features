package org.jxch.capital.io;

import org.jxch.capital.io.dto.FileMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface FileManagementService {

    String getFullPath(String path, String name);

    String createPathIfNotExist(String path);

    String upload(String path, MultipartFile file, FileMetaData metaData);

    List<FileMetaData> allFileMetaData(String path);

    String updateFileMetaData(String path, String oldName, FileMetaData metaData);

    FileMetaData getFileMetaData(String path, String name);

    void delFile(String path, String name);

    default File getFile(String path, String name) {
        return getFile(path, name, true);
    }

    File getFile(String path, String name, boolean refreshLocal);

    boolean hasFile(String path, String name);

}
