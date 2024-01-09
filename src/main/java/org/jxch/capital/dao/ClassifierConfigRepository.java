package org.jxch.capital.dao;

import org.jxch.capital.domain.po.ClassifierConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassifierConfigRepository  extends JpaRepository<ClassifierConfig, Long> {

    ClassifierConfig findByName(String name);

    @Query("select c from classifier_config c where c.name in ?1")
    List<ClassifierConfig> findAllByName(List<String> names);

}
