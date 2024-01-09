package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.ClassifierConfigDto;
import org.jxch.capital.domain.po.ClassifierConfig;
import org.jxch.capital.learning.classifier.dto.ClassifierFitInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassifierConfigMapper {

    @Mapping(target = "classifierFullName", expression = "java(toClassifierFullName(config.getClassifierName(), config.getClassifierParamTypes()))")
    ClassifierConfigDto toClassifierConfigDto(ClassifierConfig config);

    List<ClassifierConfigDto> toClassifierConfigDto(List<ClassifierConfig> classifierConfigs);

    ClassifierConfig toClassifierConfig(ClassifierConfigDto dto);

    List<ClassifierConfig> toClassifierConfig(List<ClassifierConfigDto> dto);

    @Mapping(target = "classifierFullName", expression = "java(toClassifierFullName(dto.getClassifierName(), dto.getClassifierParamTypes()))")
     ClassifierConfigDto toClassifierConfigDto(ClassifierFitInfoDto dto);

     List<ClassifierConfigDto> toClassifierConfigDtoByClassifierFitInfoDto(List<ClassifierFitInfoDto> dto);

     default String toClassifierFullName(String name, String types) {
         return name + "(" + types + ")";
     }

}
