package org.jxch.capital.learning.train;

import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;

import java.util.List;

public interface TrainDataSignalBalancePreProcessor extends ServiceWrapperSupport {

    default List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, ServiceWrapper serviceWrapper) {
        return kNodeTrains;
    }

}
