package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3MetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface Model3Management {

    void uploadModel(MultipartFile file, Model3MetaData metaData);

    List<Model3MetaData> allModelMetaData();

    void updateModelMetaData(String name, Model3MetaData metaData);

    Model3MetaData findModelMetaData(String name);

    void delModel(String name);

    File getModelFile(String name);

}
