package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.impl.MinioFilUploadService;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.dto.Model3MetaData;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TensorflowModel3Management implements Model3Management {
    private final static String BUCKEt_NAME = "capital-features-model-tensorflow-train";
    private final MinioFilUploadService minioFilUploadService;

    @Override
    public void uploadModel(MultipartFile file, @NotNull Model3MetaData metaData) {
        minioFilUploadService.upload(BUCKEt_NAME, file, metaData.setFilename(file.getOriginalFilename()).toFileMetaData());
    }

    @Override
    public List<Model3MetaData> allModelMetaData() {
        return minioFilUploadService.allFileMetaData(BUCKEt_NAME).stream()
                .map(Model3MetaData::parseOf).toList();
    }

    @Override
    public void updateModelMetaData(String name, @NotNull Model3MetaData metaData) {
        minioFilUploadService.updateFileMetaData(BUCKEt_NAME, name, metaData.toFileMetaData());
    }

    @Override
    public Model3MetaData findModelMetaData(String name) {
        return Model3MetaData.parseOf(minioFilUploadService.getFileMetaData(BUCKEt_NAME, name));
    }

    @Override
    public void delModel(String name) {
        minioFilUploadService.delFile(BUCKEt_NAME, name);
    }

}
