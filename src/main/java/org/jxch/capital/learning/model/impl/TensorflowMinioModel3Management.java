package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.impl.MinioFilManagementService;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.ModelStorageTypeEnum;
import org.jxch.capital.learning.model.ModelTypeEnum;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TensorflowMinioModel3Management implements Model3Management {
    private final static String BUCKEt_NAME = "capital-features-model-tensorflow-train";
    private final MinioFilManagementService minioFilManagementService;

    @Override
    public boolean support(MultipartFile file, @NotNull Model3BaseMetaData metaData) {
        return ModelTypeEnum.parseOf(metaData.getModeltype()).isTensorflowModel() &&
                Objects.equals(ModelStorageTypeEnum.parseOf(metaData.getStoragetype()), ModelStorageTypeEnum.MINIO);
    }

    @Override
    public void uploadModel(MultipartFile file, @NotNull Model3BaseMetaData metaData) {
        minioFilManagementService.upload(BUCKEt_NAME, file, metaData.setFilename(file.getOriginalFilename()).toFileMetaData());
    }

    @Override
    public List<Model3BaseMetaData> allModelMetaData() {
        return minioFilManagementService.allFileMetaData(BUCKEt_NAME).stream()
                .map(Model3BaseMetaData::parseOf).toList();
    }

    @Override
    public void updateModelMetaData(String name, @NotNull Model3BaseMetaData metaData) {
        minioFilManagementService.updateFileMetaData(BUCKEt_NAME, name, metaData.toFileMetaData());
    }

    @Override
    @Cacheable(value = "getFileMetaData", cacheManager = "cacheManagerShort")
    public Model3BaseMetaData findModelMetaData(String name) {
        return Model3BaseMetaData.parseOf(minioFilManagementService.getFileMetaData(BUCKEt_NAME, name));
    }

    @Override
    public void delModel(String name) {
        minioFilManagementService.delFile(BUCKEt_NAME, name);
    }

    @Override
    public File getModelFile(String name) {
        return minioFilManagementService.getFile(BUCKEt_NAME, name, false);
    }

    @Override
    public boolean hasModel(String name) {
        return minioFilManagementService.hasFile(BUCKEt_NAME, name);
    }

}
