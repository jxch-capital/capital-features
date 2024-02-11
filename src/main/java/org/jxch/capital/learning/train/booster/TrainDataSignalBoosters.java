package org.jxch.capital.learning.train.booster;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.booster.param.SignalBoosterChainParam;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.policy.AutoPolicymakerChain;

import java.util.List;

public class TrainDataSignalBoosters {

    public static List<KNodeTrain> booster(List<KNodeTrain> kNodeTrains, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodeTrains = AutoPolicymakerChain.chain(new SignalBoosterChainParam(serviceWrapper, kNodeTrains), TrainDataSignalBooster.class).getKNodeTrains();
        }
        return kNodeTrains;
    }

}
