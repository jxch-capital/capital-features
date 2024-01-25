package org.jxch.capital.learning.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ModelTypeEnum {
    TENSORFLOW_MODEL("tensorflow_model"),
    TENSORFLOW_SCALER("tensorflow_scaler");
    private final String name;

    public static ModelTypeEnum parseOf(String name) {
        return Arrays.stream(ModelTypeEnum.values()).filter(modelTypeEnum -> Objects.equals(name, modelTypeEnum.getName()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这种类型：" + name));
    }

}
