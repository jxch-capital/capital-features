package org.jxch.capital.learning.train.data;

import org.jxch.capital.learning.train.param.PredictionDataParam;
import org.jxch.capital.learning.train.param.PredictionDataRes;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;

public interface TrainService {

    TrainDataRes trainData(Long trainConfigId);

    PredictionDataRes predictionData(PredictionDataParam param);

    TrainDataService findServiceByTrainConfigId(Long trainConfigId);

    TrainDataParam findParamsByTrainConfigId(Long trainConfigId);

}
