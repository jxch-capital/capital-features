package org.jxch.capital.learning.classifier;

import org.jxch.capital.domain.dto.ClassifierModelConfigDto;

import java.util.List;

public interface ClassifierModelConfigService {

    List<ClassifierModelConfigDto> findAll();

    ClassifierModelConfigDto findById(Long id);

    List<ClassifierModelConfigDto> findById(List<Long> ids);

    ClassifierModelConfigDto findByName(String name);

    List<ClassifierModelConfigDto> findByName(List<String> names);

    Integer save(List<ClassifierModelConfigDto> dto);

    void del(List<Long> ids);

}
