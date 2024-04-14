package org.jxch.capital.dao;

import org.jxch.capital.domain.po.CNDailyKHashIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CNDailyKHashIndexRepository extends JpaRepository<CNDailyKHashIndex, CNDailyKHashIndex.CNDailyKHashIndexId> {

    List<CNDailyKHashIndex> findAllByHashBetween(Long from, Long to);

    List<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLength(Long from, Long to, Boolean isFillLength);

    List<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(Long from, Long to, Boolean isFillLength, Integer leftVacancies);

}
