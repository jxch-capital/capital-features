package org.jxch.capital.learning.train.data.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.jxch.capital.learning.train.data.TrainDataDistillerService;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.*;
import org.jxch.capital.learning.train.param.dto.TrainDataCentrifugeParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataCentrifugeServiceImpl implements TrainDataDistillerService {
    private final TrainConfigService trainConfigService;
    private final Model3Management model3Management;
    private final Model3Prediction model3Prediction;
    private final TrainService trainService;

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        var centrifugeParam = (TrainDataCentrifugeParam) param;

        Model3BaseMetaData modelMetaData = model3Management.findModelMetaData(centrifugeParam.getModel());
        TrainDataRes trainDataRes = trainService.trainData(centrifugeParam.getTrainConfigId());

        double[][][] features = trainDataRes.getFeatures();
        double[] prediction = model3Prediction.predictionComplete(features, model3Management.getModelFile(centrifugeParam.getModel()), modelMetaData);
        int[] signals = trainDataRes.getSignals(SignalType.valueOf(centrifugeParam.getType()));


        return null;
    }

    @Override
    public PredictionDataOneStockRes predictionOneStockData(@NotNull PredictionDataOneStockParam param, boolean offset) {
        var centrifugeParam = getParam(trainConfigService.findParamsById(param.getTrainConfigId()), TrainDataCentrifugeParam.class);
        Long modelTrainConfigId = model3Management.findModelMetaData(centrifugeParam.getModel()).getTrainconfigid();
        return trainService.predictionOneStockData(param.setTrainConfigId(modelTrainConfigId));
    }

    @Override
    public TrainDataParam getDefaultParam() {
        return new TrainDataCentrifugeParam();
    }

    @Override
    public String name() {
        return "模型特征数据离心蒸馏器";
    }

}
