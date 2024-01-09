package org.jxch.capital.dao;

import org.jxch.capital.domain.po.ClassifierModelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassifierModelConfigRepository extends JpaRepository<ClassifierModelConfig, Long> {

    ClassifierModelConfig findByName(String name);

    @Query("select c from classifier_model_config c where c.name in ?1")
    List<ClassifierModelConfig> findAllByName(List<String> names);

}
