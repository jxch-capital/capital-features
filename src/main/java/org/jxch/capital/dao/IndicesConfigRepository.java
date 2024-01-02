package org.jxch.capital.dao;

import org.jxch.capital.domain.po.IndicesConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndicesConfigRepository extends JpaRepository<IndicesConfig, Long> {


    IndicesConfig findByName(String name);

    @Query("select c from indices_config c where c.name in ?1")
    List<IndicesConfig> findByName(List<String> name);

}
