package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3ManagementService implements Model3Management {

    @Override
    public boolean support(MultipartFile file, Model3BaseMetaData metaData) {
        return false;
    }

    public List<Model3Management> allModel3Management() {
        return AppContextHolder.allService(Model3Management.class).stream()
                .filter(service -> !Objects.equals(this, service)).toList();
    }

    @Override
    public void uploadModel(MultipartFile file, Model3BaseMetaData metaData) {
        if (allModelMetaData().stream().anyMatch(theMetaData -> Objects.equals(file.getOriginalFilename(), theMetaData.getFilename()))) {
            throw new IllegalArgumentException("模型名称重复, 上传操作被拒绝");
        }
        allModel3Management().stream().filter(service -> service.support(file, metaData))
                .findAny().orElseThrow(() -> new IllegalArgumentException("不被支持的类型")).uploadModel(file, metaData);
    }

    @Override
    public List<Model3BaseMetaData> allModelMetaData() {
        return allModel3Management().stream().map(Model3Management::allModelMetaData).flatMap(List::stream).toList();
    }

    @Override
    public void updateModelMetaData(String name, Model3BaseMetaData metaData) {
        throw new UnsupportedOperationException("暂不支持更新模型元数据");
    }

    public Model3Management getSupportManagement(String name) {
        return allModel3Management().stream().filter(service -> service.hasModel(name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("不存在这个模型：" + name));
    }

    @Override
    public Model3BaseMetaData findModelMetaData(String name) {
        return getSupportManagement(name).findModelMetaData(name);
    }

    @Override
    public void delModel(String name) {
        getSupportManagement(name).delModel(name);
    }

    @Override
    public File getModelFile(String name) {
        return getSupportManagement(name).getModelFile(name);
    }

    @Override
    public boolean hasModel(String name) {
        return allModel3Management().stream().anyMatch(service -> service.hasModel(name));
    }

}
