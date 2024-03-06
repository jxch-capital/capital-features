package org.jxch.capital.learning.old.train.booster.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.old.train.booster.param.FutureSignalBoosterParam;
import org.jxch.capital.learning.old.train.booster.param.SignalBoosterChainParam;
import org.jxch.capital.learning.old.train.param.SignalType;
import org.jxch.capital.learning.old.train.booster.TrainDataSignalBooster;
import org.jxch.capital.support.policy.PolicymakerChainParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataOptimalFutureSignalBooster implements TrainDataSignalBooster {

    private KNodeTrain booster(KNodeTrain kNodeTrain, @NotNull SignalType signalType, int offset) {
        return switch (signalType) {
            case DEFAULT, UP -> {
                if (kNodeTrain.isUp() && kNodeTrain.hasFutureDown(offset)) {
                    kNodeTrain.resetUpToFlat();
                }
                yield kNodeTrain;
            }
            case DOWN -> {
                if (kNodeTrain.isDown() && kNodeTrain.hasFutureUp(offset)) {
                    kNodeTrain.resetDownToFlat();
                }
                yield kNodeTrain;
            }
            case UP_DOWN -> {
                if (kNodeTrain.isUp() && kNodeTrain.hasFutureDown(offset)) {
                    kNodeTrain.resetUpToFlat();
                } else if (kNodeTrain.isDown() && kNodeTrain.hasFutureUp(offset)) {
                    kNodeTrain.resetDownToFlat();
                }
                yield kNodeTrain;
            }
        };
    }

    @Override
    public PolicymakerChainParam apply(PolicymakerChainParam applyParam) {
        log.debug(name());
        var chainParam = (SignalBoosterChainParam) applyParam;
        var boosterParam = chainParam.getServiceWrapper().getParamObj(FutureSignalBoosterParam.class);

        List<KNodeTrain> kNodeTrains = chainParam.getKNodeTrains().parallelStream().map(kNodeTrain ->
                booster(kNodeTrain, boosterParam.getSType(), boosterParam.getOffset())).toList();

        return chainParam.setKNodeTrains(kNodeTrains);
    }

    @Override
    public Object getDefaultParam() {
        return new FutureSignalBoosterParam();
    }

    @Override
    public String name() {
        return "最优未来信号增益器";
    }
}
