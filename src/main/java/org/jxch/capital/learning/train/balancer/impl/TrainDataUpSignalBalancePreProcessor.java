package org.jxch.capital.learning.train.balancer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.balancer.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.train.param.dto.TrainDataSignalBalanceParam;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataUpSignalBalancePreProcessor implements TrainDataSignalBalancePreProcessor {

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        double threshold = serviceWrapper.getParamObj(TrainDataSignalBalanceParam.class).getThreshold();
        return shuffle(threshold(remove(kNodeTrains, KNodeTrain::isResetToFlat), KNodeTrain::isUp, threshold, serviceWrapper));
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataSignalBalanceParam();
    }

    @Override
    public String name() {
        return "上涨信号平衡器";
    }

}
