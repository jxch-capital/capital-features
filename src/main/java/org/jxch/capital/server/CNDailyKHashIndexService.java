package org.jxch.capital.server;

import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.khash.DailyGridKHashCNDailyIndexAgg;
import org.jxch.capital.khash.KReader;

import java.math.BigDecimal;
import java.util.List;

public interface CNDailyKHashIndexService {

    Integer saveByAgg(KReader reader, DailyGridKHashCNDailyIndexAgg agg);

    List<CNDailyKHashIndexDto> findByAgg(KReader reader, DailyGridKHashCNDailyIndexAgg agg);

    List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Boolean isFillLength, Integer leftVacancies);

    List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength);

}
