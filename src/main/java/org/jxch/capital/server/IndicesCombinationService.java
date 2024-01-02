package org.jxch.capital.server;

import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.IndicesCombinationDto;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.List;

public interface IndicesCombinationService {

    List<IndicesCombinationDto> findAll();

    IndicesCombinationDto findById(Long id);

    List<IndicesCombinationDto> findById(List<Long> ids);

    Integer save(List<IndicesCombinationDto> dto);

    void del(List<Long> ids);

    List<Indicator<Num>> getIndicatorsById(Long id, BarSeries barSeries);

    List<IndicatorWrapper> getIndicatorWrapper(Long id);

}
