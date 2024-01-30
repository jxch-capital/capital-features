package org.jxch.capital.learning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum PredictSignalTypeEnum {
    FOLLOW_UP("follow_up"),
    FOLLOW_DOWN("follow_down"),
    FOLLOW_UP_DOWN("follow_up_down"),
    ;
    private final String name;

    public static PredictSignalTypeEnum parseOf(String name) {
        return Arrays.stream(PredictSignalTypeEnum.values()).filter(modelTypeEnum -> Objects.equals(name, modelTypeEnum.getName()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这种类型：" + name));
    }
}
