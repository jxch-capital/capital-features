package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface Model3Management {

    void uploadModel(MultipartFile file, Model3BaseMetaData metaData);

    List<Model3BaseMetaData> allModelMetaData();

    void updateModelMetaData(String name, Model3BaseMetaData metaData);

    Model3BaseMetaData findModelMetaData(String name);

    void delModel(String name);

    File getModelFile(String name);

}
