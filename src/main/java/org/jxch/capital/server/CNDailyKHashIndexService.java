package org.jxch.capital.server;

import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.khash.DailyGridKHashCNDailyIndexAgg;
import org.jxch.capital.khash.KReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface CNDailyKHashIndexService {

    Integer saveByAgg(KReader reader, DailyGridKHashCNDailyIndexAgg agg);

    List<List<CNDailyKHashIndexDto>> findByAgg(KReader reader, DailyGridKHashCNDailyIndexAgg agg);

    List<Page<CNDailyKHashIndexDto>> findByAgg(KReader reader, DailyGridKHashCNDailyIndexAgg agg, Pageable pageable);

    List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Boolean isFillLength, Integer leftVacancies);

    Page<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Boolean isFillLength, Integer leftVacancies, Pageable pageable);

    List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength);

    Page<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Pageable pageable);

}
