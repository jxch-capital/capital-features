package org.jxch.capital.learning.old.train.booster.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.old.train.booster.param.SignalBoosterChainParam;
import org.jxch.capital.learning.old.train.booster.TrainDataSignalBooster;
import org.jxch.capital.learning.old.train.booster.param.DominanceSignalBoosterParam;
import org.jxch.capital.support.policy.PolicymakerChainParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataDominanceSignalBooster implements TrainDataSignalBooster {

    @Override
    public PolicymakerChainParam apply(PolicymakerChainParam applyParam) {
        var chainParam = (SignalBoosterChainParam) applyParam;
        var dominanceParam = chainParam.getServiceWrapper().getParamObj(DominanceSignalBoosterParam.class);

        chainParam.getKNodeTrains().parallelStream().forEach(kNodeTrain -> {
            double futureMax = kNodeTrain.getFutureMax();
            double futureMin = kNodeTrain.getFutureMin();
            double endClose = kNodeTrain.getEndClose();
            double maxClose = futureMax - endClose;
            double closeMin = endClose - futureMin;

            if (futureMax > endClose && futureMin > endClose) {
                kNodeTrain.resetToUp();
            } else if (futureMax < endClose && futureMin < endClose) {
                kNodeTrain.resetToDown();
            } else if (maxClose > closeMin && maxClose / closeMin > dominanceParam.getTh()) {
                kNodeTrain.resetToUp();
            } else if (closeMin > maxClose && closeMin / maxClose > dominanceParam.getTh()) {
                kNodeTrain.resetToDown();
            } else {
                switch (dominanceParam.getDominanceStrategyType()) {
                    case FLAT -> kNodeTrain.resetToFlat();
                    case MAX -> {
                        if (maxClose > closeMin) {
                            kNodeTrain.resetToUp();
                        } else if (closeMin > maxClose) {
                            kNodeTrain.resetToDown();
                        } else {
                            kNodeTrain.resetToFlat();
                        }
                    }
                    case PROXIMITY -> {
                        if (kNodeTrain.getFutureMaxIndex() < kNodeTrain.getFutureMinIndex()) {
                            kNodeTrain.resetToUp();
                        } else if (kNodeTrain.getFutureMaxIndex() > kNodeTrain.getFutureMinIndex()) {
                            kNodeTrain.resetToDown();
                        } else {
                            kNodeTrain.resetToFlat();
                        }
                    }
                    case FINAL -> {
                        if (kNodeTrain.getFutureMaxIndex() < kNodeTrain.getFutureMinIndex()) {
                            kNodeTrain.resetToDown();
                        } else if (kNodeTrain.getFutureMaxIndex() > kNodeTrain.getFutureMinIndex()) {
                            kNodeTrain.resetToUp();
                        } else {
                            kNodeTrain.resetToFlat();
                        }
                    }
                }
            }
        });

        return chainParam;
    }

    @Override
    public Object getDefaultParam() {
        return new DominanceSignalBoosterParam();
    }

    @Override
    public String name() {
        return "优势信号增益器";
    }
}
