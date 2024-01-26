package org.jxch.capital.utils;

import org.jxch.capital.domain.dto.EChartsMainIndexDto;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.server.IndexService;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class EChartsU {

    public static List<EChartsMainIndexDto<Double>> emaXEChartsDto(List<KLine> kLines, int length) {
        List<KLineIndices> kLineIndices = AppContextHolder.findFirstService(IndexService.class).index(kLines, Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder().name("EMA-" + length).indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), length)).build()));
        return kLineIndices.stream().map(ki -> new EChartsMainIndexDto<>(ki.getDate(), ki.get("EMA-" + length))).toList();
    }

}
