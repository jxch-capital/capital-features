package org.jxch.capital.server;

import org.jxch.capital.domain.dto.IndicesConfigDto;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.List;

public interface IndicesConfigService {

    Integer save(List<IndicesConfigDto> dtoList);

    void del(List<Long> ids);

    List<IndicesConfigDto> findAll();

    List<IndicesConfigDto> findById(List<Long> ids);

    IndicesConfigDto findById(Long id);

    IndicesConfigDto findByName(String name);

    List<IndicesConfigDto> findByName(List<String> name);

    Indicator<Num> getIndicatorById(Long id, BarSeries barSeries);

    List<String> allSupportIndicators();

    List<Class<?>> allSupportIndicatorParamTypes();

    List<Class<?>> getParamTypes(IndicesConfigDto dto);

    boolean hasIndicatorParam(IndicesConfigDto dto);

}
