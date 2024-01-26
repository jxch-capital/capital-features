package org.jxch.capital.learning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ModelStorageTypeEnum {
    LOCAL("local"),
    MINIO("minio"),
    ;
    private final String name;

    public static ModelStorageTypeEnum parseOf(String name) {
        return Arrays.stream(ModelStorageTypeEnum.values()).filter(modelTypeEnum -> Objects.equals(name, modelTypeEnum.getName()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这种类型：" + name));
    }

}
