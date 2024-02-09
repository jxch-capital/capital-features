package org.jxch.capital.learning.train.data.impl;

import com.alibaba.fastjson2.JSONObject;
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
import org.jxch.capital.learning.train.param.dto.TrainDataDistillerParam;
import org.jxch.capital.learning.train.param.dto.TrainDataDistillerRes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataDistillerServiceImpl implements TrainDataDistillerService {
    private final TrainConfigService trainConfigService;
    private final Model3Management model3Management;
    private final Model3Prediction model3Prediction;
    private final TrainService trainService;

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        var distillerParam = (TrainDataDistillerParam) param;

        TestDataRes testDataRes = trainService.testData(distillerParam.getTrainConfigId(), distillerParam.getModel(), distillerParam.getEType());
        double[] prediction = testDataRes.getPrediction();

        List<double[][]> filteredFeatures = new ArrayList<>();
        List<Integer> filteredSignals = new ArrayList<>();
        for (int i = 0; i < prediction.length; i++) {
            if (prediction[i] > distillerParam.getUpTh() || prediction[i] < distillerParam.getDownTh()) {
                filteredFeatures.add(testDataRes.getFeatures()[i]);
                filteredSignals.add(testDataRes.getSignals()[i]);
            }
        }

        int[] filteredSignalsArray = filteredSignals.stream().mapToInt(v -> v).toArray();
        double[][][] filteredFeaturesArray = new double[filteredFeatures.size()][][];
        for (int i = 0; i < filteredFeatures.size(); i++) {
            filteredFeaturesArray[i] = filteredFeatures.get(i);
        }

        return TrainDataDistillerRes.builder().features(filteredFeaturesArray).signals(filteredSignalsArray).build();
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
