package org.jxch.capital.utils;

import lombok.NonNull;
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

public class IndicesU {

    public static List<EChartsMainIndexDto<Double>> emaXEChartsDto(@NonNull IndexService indexService, List<KLine> kLines, int x) {
        List<KLineIndices> kLineIndices = indexService.index(kLines, Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder().name("EMA-" + x).indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), x)).build()));
        return kLineIndices.stream().map(ki -> new EChartsMainIndexDto<>(ki.getDate(), ki.get("EMA-" + x))).toList();
    }

}
