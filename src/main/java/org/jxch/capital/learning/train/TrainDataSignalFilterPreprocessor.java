package org.jxch.capital.learning.train;

import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;

import java.util.List;

public interface TrainDataSignalFilterPreprocessor extends ServiceWrapperSupport {

    default KNodeParam kNodeParamPreprocess(KNodeParam kNodeParam, ServiceWrapper serviceWrapper) {
        return kNodeParam;
    }

    default List<KNode> kNodesPostProcess(List<KNode> kNodes, ServiceWrapper serviceWrapper) {
        return kNodes;
    }

    default List<KNodeTrain>  kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, ServiceWrapper serviceWrapper) {
        return kNodeTrains;
    }

}
