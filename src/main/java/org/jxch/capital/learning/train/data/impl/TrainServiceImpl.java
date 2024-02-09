package org.jxch.capital.learning.train.data.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.train.Trains;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.jxch.capital.learning.train.data.TrainDataService;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.*;
import org.jxch.capital.learning.train.param.dto.TestDataRes;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainConfigService trainConfigService;
    private final Model3Prediction model3Prediction;

    @Override
    public TrainDataRes trainData(Long trainConfigId) {
        return findServiceByTrainConfigId(trainConfigId).trainData(findParamsByTrainConfigId(trainConfigId));
    }

    @Override
    public PredictionDataOneStockRes predictionOneStockData(@NotNull PredictionDataOneStockParam param) {
        return findServiceByTrainConfigId(param.getTrainConfigId()).predictionOneStockData(param);
    }

    @Override
    public TrainDataService findServiceByTrainConfigId(Long trainConfigId) {
        return Trains.getTrainDataService(trainConfigService.findById(trainConfigId).getService());
    }

    @Override
    public TrainDataParam findParamsByTrainConfigId(Long trainConfigId) {
        return findServiceByTrainConfigId(trainConfigId).getParam(trainConfigService.findParamsById(trainConfigId));
    }

    @Override
    public TestDataRes testData(Long trainConfigId, String model, SignalType type) {
        TrainDataRes trainDataRes = trainData(trainConfigId);
        double[][][] features = trainDataRes.getFeatures();
        double[] prediction = model3Prediction.predictionComplete(features, model);
        int[] signals = trainDataRes.getSignals(type);

        return TestDataRes.builder()
                .features(features)
                .prediction(prediction)
                .signals(signals)
                .build();
    }

}
