package org.jxch.capital.dao;

import org.jxch.capital.domain.po.IndicesCombination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IndicesCombinationRepository extends JpaRepository<IndicesCombination, Long> {

    IndicesCombination findByName(String name);

    @Query("select c from indices_combination c where c.name in ?1")
    List<IndicesCombination> findAllByName(List<String> names);

}
