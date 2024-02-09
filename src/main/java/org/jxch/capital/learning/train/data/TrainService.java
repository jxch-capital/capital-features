package org.jxch.capital.learning.train.data;

import org.jxch.capital.learning.train.param.*;
import org.jxch.capital.learning.train.param.dto.TestDataRes;

public interface TrainService {

    TrainDataRes trainData(Long trainConfigId);

    PredictionDataOneStockRes predictionOneStockData(PredictionDataOneStockParam param);

    TrainDataService findServiceByTrainConfigId(Long trainConfigId);

    TrainDataParam findParamsByTrainConfigId(Long trainConfigId);

    TestDataRes testData(Long trainConfigId, String model, SignalType type);
}
