package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {
    private final KLineMapper kLineMapper;

    @Override
    public List<KLineIndices> index(List<KLine> kLines, Duration barDuration, @NonNull List<IndicatorWrapper> indicators) {
        List<KLineIndices> kLineIndices = kLineMapper.toKLineIndices(kLines);
        BarSeries barSeries = kLineMapper.toBarSeries(kLines, barDuration);

        indicators.forEach(wrapper -> {
            Indicator<Num> indicator = wrapper.getIndicator(barSeries);
            IntStream.range(0, kLineIndices.size())
                    .forEach(i -> kLineIndices.get(i).setIndex(wrapper.getName(), indicator.getValue(i).doubleValue()));
        });

        return kLineIndices;
    }

}
