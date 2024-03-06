package org.jxch.capital.learning.old.train.filter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.old.train.param.dto.TrainDataEMAReverseSignalFilterParam;
import org.jxch.capital.learning.old.train.filter.TrainDataFollowTrendSignalFilterPreprocessor;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.utils.CollU;
import org.springframework.stereotype.Service;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataEMAFollowSignalTestFilterImplPreprocessor implements TrainDataFollowTrendSignalFilterPreprocessor {
    private final static String PREFIX = "EMA-";

    @Override
    public KNodeParam kNodeParamPreprocess(@NotNull KNodeParam kNodeParam, @NotNull ServiceWrapper serviceWrapper) {
        var param = serviceWrapper.getParamObj(TrainDataEMAReverseSignalFilterParam.class);
        String indicatorName = PREFIX + param.getLength();
        if (!kNodeParam.hasIndicator(indicatorName)) {
            return kNodeParam.addIndicator(IndicatorWrapper.builder().name(indicatorName).immutable(true)
                    .indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), param.getLength())).build());
        }
        return kNodeParam;
    }

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        var param = serviceWrapper.getParamObj(TrainDataEMAReverseSignalFilterParam.class);
        return kNodeTrains.stream().filter(kNodeTrain -> {
            if (!kNodeTrain.isFlat()) {
                KLineIndices kLineIndices = (KLineIndices) CollU.last(kNodeTrain.getKLines());
                Double ema = kLineIndices.get(PREFIX + param.getLength());
                return (kNodeTrain.isUp() && kLineIndices.getClose() > ema) || (kNodeTrain.isDown() && kLineIndices.getClose() < ema);
            } else {
                return false;
            }
        }).toList();
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataEMAReverseSignalFilterParam();
    }

    @Override
    public String name() {
        return "EMA测试集顺势信号过滤器";
    }
}
