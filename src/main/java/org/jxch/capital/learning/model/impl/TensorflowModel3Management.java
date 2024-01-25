package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.impl.MinioFilManagementService;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TensorflowModel3Management implements Model3Management {
    private final static String BUCKEt_NAME = "capital-features-model-tensorflow-train";
    private final MinioFilManagementService minioFilUploadService;

    @Override
    public void uploadModel(MultipartFile file, @NotNull Model3BaseMetaData metaData) {
        minioFilUploadService.upload(BUCKEt_NAME, file, metaData.setFilename(file.getOriginalFilename()).toFileMetaData());
    }

    @Override
    public List<Model3BaseMetaData> allModelMetaData() {
        return minioFilUploadService.allFileMetaData(BUCKEt_NAME).stream()
                .map(Model3BaseMetaData::parseOf).toList();
    }

    @Override
    public void updateModelMetaData(String name, @NotNull Model3BaseMetaData metaData) {
        minioFilUploadService.updateFileMetaData(BUCKEt_NAME, name, metaData.toFileMetaData());
    }

    @Override
    public Model3BaseMetaData findModelMetaData(String name) {
        return Model3BaseMetaData.parseOf(minioFilUploadService.getFileMetaData(BUCKEt_NAME, name));
    }

    @Override
    public void delModel(String name) {
        minioFilUploadService.delFile(BUCKEt_NAME, name);
    }

    @Override
    public File getModelFile(String name) {
        return minioFilUploadService.getFile(BUCKEt_NAME, name);
    }

}
