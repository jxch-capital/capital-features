package org.jxch.capital.learning.old.train.filter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.old.train.param.dto.TrainDataFutureThParam;
import org.jxch.capital.learning.old.train.filter.TrainDataFollowTrendSignalFilterPreprocessor;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataFutureThFilter implements TrainDataFollowTrendSignalFilterPreprocessor {

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        var param = serviceWrapper.getParamObj(TrainDataFutureThParam.class);
        kNodeTrains.forEach(kNodeTrain -> {
            if (kNodeTrain.absFuturePercent() < param.getThreshold()) {
                kNodeTrain.resetToFlat();
            }
        });

        return kNodeTrains;
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataFutureThParam();
    }

    @Override
    public String name() {
        return "信号强度过滤器";
    }

}
