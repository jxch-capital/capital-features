package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.ClassifierModelConfigDto;
import org.jxch.capital.domain.po.ClassifierModelConfig;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassifierModelConfigMapper {

    @Mapping(target = "hasLocalModel", expression = "java(hasLocalModel(config.getName()))")
    ClassifierModelConfigDto toClassifierModelConfigDto(ClassifierModelConfig config);

    List<ClassifierModelConfigDto> toClassifierModelConfigDto(List<ClassifierModelConfig> configs);

    ClassifierModelConfig toClassifierModelConfig(ClassifierModelConfigDto dto);

    List<ClassifierModelConfig> toClassifierModelConfig(List<ClassifierModelConfigDto> dto);

    default boolean hasLocalModel(String modelName) {
        return ClassifierLearnings.hasLocalModel(modelName);
    }

}
