package org.jxch.capital.learning.train.data.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.jxch.capital.learning.train.data.TrainDataDistillerService;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;
import org.jxch.capital.learning.train.param.dto.TestDataRes;
import org.jxch.capital.learning.train.param.dto.TrainDataCentrifugeParam;
import org.jxch.capital.learning.train.param.dto.TrainDataCentrifugeRes;
import org.jxch.capital.utils.CollU;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        TestDataRes testDataRes = trainService.testData(centrifugeParam.getTrainConfigId(), centrifugeParam.getModel(), centrifugeParam.getEType());
        double[] prediction = testDataRes.getPrediction();

        List<double[][]> filteredFeatures = new ArrayList<>();
        List<Integer> filteredSignals = new ArrayList<>();
        List<double[][]> filteredOppositeFeatures = new ArrayList<>();
        List<Integer> filteredOppositeSignals = new ArrayList<>();

        for (int i = 0; i < prediction.length; i++) {
            boolean isBelowDownTh = prediction[i] < centrifugeParam.getDownTh();
            boolean isAboveUpTh = prediction[i] > centrifugeParam.getUpTh();

            if (isBelowDownTh || isAboveUpTh) {
                boolean isSignalOne = testDataRes.getSignals()[i] == 1;
                List<double[][]> targetFeatureList = (isSignalOne ^ isBelowDownTh) ? filteredFeatures : filteredOppositeFeatures;
                List<Integer> targetSignalList = (isSignalOne ^ isBelowDownTh) ? filteredSignals : filteredOppositeSignals;

                targetFeatureList.add(testDataRes.getFeatures()[i]);
                targetSignalList.add(testDataRes.getSignals()[i]);
            }
        }

        return TrainDataCentrifugeRes.builder()
                .signals(CollU.toIntArr1(filteredSignals))
                .oppositeSignals(CollU.toIntArr1(filteredOppositeSignals))
                .features(CollU.toDoubleArr3(filteredFeatures))
                .oppositeFeatures(CollU.toDoubleArr3(filteredOppositeFeatures))
                .build();
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
