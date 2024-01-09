package org.jxch.capital.learning.classifier;

import org.jxch.capital.domain.dto.ClassifierConfigDto;
import org.jxch.capital.learning.classifier.dto.ClassifierFitInfoDto;

import java.util.List;

public interface ClassifierConfigService {

    List<ClassifierConfigDto> findAll();

    ClassifierConfigDto findById(Long id);

    List<ClassifierConfigDto> findById(List<Long> ids);

    ClassifierConfigDto findByName(String name);

    Integer save(List<ClassifierConfigDto> dto);

    void del(List<Long> ids);

    List<ClassifierFitInfoDto> getAllClassificationSupportInfo();

    ClassifierFitInfoDto findClassifierFitInfoDto(String className, String fitTypes);

     List<ClassifierConfigDto> getAllClassifierSupportDto() ;

}
