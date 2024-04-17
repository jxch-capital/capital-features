package org.jxch.capital.dao;

import org.jxch.capital.domain.po.CNDailyKHashIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CNDailyKHashIndexRepository extends JpaRepository<CNDailyKHashIndex, CNDailyKHashIndex.CNDailyKHashIndexId> {

    List<CNDailyKHashIndex> findAllByHashBetween(Long from, Long to);

    Page<CNDailyKHashIndex> findAllByHashBetween(Long from, Long to, Pageable pageable);

    List<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLength(Long from, Long to, Boolean isFillLength);

    Page<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLength(Long from, Long to, Boolean isFillLength, Pageable pageable);

    List<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(Long from, Long to, Boolean isFillLength, Integer leftVacancies);

    Page<CNDailyKHashIndex> findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(Long from, Long to, Boolean isFillLength, Integer leftVacancies, Pageable pageable);

}
