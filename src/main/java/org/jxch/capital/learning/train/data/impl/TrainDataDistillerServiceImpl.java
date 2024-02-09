package org.jxch.capital.learning.train.data.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.jxch.capital.learning.train.data.TrainDataPurifierService;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;
import org.jxch.capital.learning.train.param.dto.TestDataRes;
import org.jxch.capital.learning.train.param.dto.TrainDataDistillerParam;
import org.jxch.capital.learning.train.param.dto.TrainDataDistillerRes;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataDistillerServiceImpl implements TrainDataPurifierService {
    private final TrainConfigService trainConfigService;
    private final Model3Management model3Management;
    private final TrainService trainService;

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        var distillerParam = (TrainDataDistillerParam) param;
        TestDataRes testDataRes = trainService.testData(distillerParam.getTrainConfigId(), distillerParam.getModel(), distillerParam.getEType());
        TrainDataDistillerRes res = new TrainDataDistillerRes();
        testDataRes.foreach((feature, prediction, signal) -> {
            if (prediction > distillerParam.getUpTh() || prediction < distillerParam.getDownTh()) {
                res.getTargetFeatures().add(feature);
                res.getTargetSignals().add(signal);
            }
        });
        return res;
    }

    @Override
    public PredictionDataOneStockRes predictionOneStockData(@NotNull PredictionDataOneStockParam param, boolean offset) {
        var distillerParam = getParam(trainConfigService.findParamsById(param.getTrainConfigId()), TrainDataDistillerParam.class);
        Long modelTrainConfigId = model3Management.findModelMetaData(distillerParam.getModel()).getTrainconfigid();
        return trainService.predictionOneStockData(param.setTrainConfigId(modelTrainConfigId));
    }

    @Override
    public TrainDataParam getDefaultParam() {
        return new TrainDataDistillerParam();
    }

    @Override
    public TrainDataParam getParam(String json) {
        return JSONObject.parseObject(json, TrainDataDistillerParam.class);
    }

    @Override
    public String name() {
        return "模型特征数据蒸馏器";
    }

}
