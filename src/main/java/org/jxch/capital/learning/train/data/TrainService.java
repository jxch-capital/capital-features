package org.jxch.capital.learning.train.data;

import org.jxch.capital.learning.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;

public interface TrainService {

    TrainDataRes trainData(Long trainConfigId);

    PredictionDataOneStockRes predictionOneStockData(PredictionDataOneStockParam param);

    TrainDataService findServiceByTrainConfigId(Long trainConfigId);

    TrainDataParam findParamsByTrainConfigId(Long trainConfigId);

}
