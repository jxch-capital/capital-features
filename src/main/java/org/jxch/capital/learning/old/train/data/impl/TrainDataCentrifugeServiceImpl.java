package org.jxch.capital.learning.old.train.data.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.old.train.data.TrainService;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.old.train.param.TrainDataParam;
import org.jxch.capital.learning.old.train.param.TrainDataRes;
import org.jxch.capital.learning.old.train.param.dto.TestDataRes;
import org.jxch.capital.learning.old.train.param.dto.TrainDataCentrifugeParam;
import org.jxch.capital.learning.old.train.param.dto.TrainDataCentrifugeRes;
import org.jxch.capital.learning.old.train.config.TrainConfigService;
import org.jxch.capital.learning.old.train.data.TrainDataPurifierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataCentrifugeServiceImpl implements TrainDataPurifierService {
    private final TrainConfigService trainConfigService;
    private final Model3Management model3Management;
    private final TrainService trainService;

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        var centrifugeParam = (TrainDataCentrifugeParam) param;
        TestDataRes testDataRes = trainService.testData(centrifugeParam.getTrainConfigId(), centrifugeParam.getModel(), centrifugeParam.getEType());
        TrainDataCentrifugeRes res = new TrainDataCentrifugeRes();
        testDataRes.foreach((feature, prediction, signal) -> {
            boolean isBelowDownTh = prediction < centrifugeParam.getDownTh();
            boolean isAboveUpTh = prediction > centrifugeParam.getUpTh();
            if (isBelowDownTh || isAboveUpTh) {
                boolean isSignalOne = signal == 1;
                List<double[][]> targetFeatureList = (isSignalOne ^ isBelowDownTh) ? res.getTargetFeatures() : res.getOppositeFeatures();
                List<Integer> targetSignalList = (isSignalOne ^ isBelowDownTh) ? res.getTargetSignals() : res.getOppositeSignals();
                targetFeatureList.add(feature);
                targetSignalList.add(signal);
            }
        });
        return res;
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
