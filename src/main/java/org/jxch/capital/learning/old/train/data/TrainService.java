package org.jxch.capital.learning.old.train.data;

import org.jxch.capital.learning.old.train.param.*;
import org.jxch.capital.learning.old.train.param.dto.TestDataRes;
import org.jxch.capital.learning.train.param.*;

public interface TrainService {

    TrainDataRes trainData(Long trainConfigId);

    PredictionDataOneStockRes predictionOneStockData(PredictionDataOneStockParam param);

    TrainDataService findServiceByTrainConfigId(Long trainConfigId);

    TrainDataParam findParamsByTrainConfigId(Long trainConfigId);

    TestDataRes testData(Long trainConfigId, String model, SignalType type);
}
