package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.train.dto.TrainDataSignalBalanceParam;
import org.jxch.capital.learning.train.dto.TrainDataUpSignalBalanceParam;
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
        return shuffle(threshold(remove(kNodeTrains, KNodeTrain::isReset), KNodeTrain::isUp, threshold, serviceWrapper));
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataUpSignalBalanceParam();
    }

    @Override
    public String name() {
        return "上涨信号平衡器";
    }

}
