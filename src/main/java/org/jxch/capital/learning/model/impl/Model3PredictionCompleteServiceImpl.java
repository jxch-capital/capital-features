package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.Model3PredictionCompleteService;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.train.param.dto.PredictionOneStockParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class Model3PredictionCompleteServiceImpl implements Model3PredictionCompleteService {
    private final Model3PredictSignalAutoProcessor model3PredictSignalAutoProcessor;
    private final Model3PredictionService model3PredictionService;
    private final Model3Management model3Management;
    private final TrainService trainService;

    @Override
    public double[] prediction(double[][][] data, String modelName) {
        return model3PredictionService.predictionComplete(data, model3Management.getModelFile(modelName), model3Management.findModelMetaData(modelName));
    }

    @Override
    public double[] prediction(String modelName, @NotNull PredictionOneStockParam predictionParam) {
        return prediction(trainService.predictionOneStockData(predictionParam).getFeatures(), modelName);
    }

    public PredictionOneStockParam setTrainConfigId(String modelName, @NotNull PredictionOneStockParam predictionParam) {
        return predictionParam.setTrainConfigId(model3Management.findModelMetaData(modelName).getTrainconfigid());
    }

    @Override
    public Model3PredictRes predictionCarry(String modelName, @NotNull PredictionOneStockParam predictionParam) {
        predictionParam = setTrainConfigId(modelName, predictionParam);
        PredictionDataOneStockRes predictionDataOneStockRes = trainService.predictionOneStockData(predictionParam);
        double[] prediction = prediction(predictionDataOneStockRes.getFeatures(), modelName);
        return model3PredictSignalAutoProcessor.signalProcessor(predictionDataOneStockRes, prediction, modelName, predictionParam);
    }

    @Override
    public Model3PredictRes predictionCarry(@NotNull List<String> modelNames, PredictionOneStockParam predictionParam) {
        return modelNames.stream().map(modelName -> {
                    setTrainConfigId(modelName, predictionParam);
                    PredictionDataOneStockRes predictionDataOneStockRes = trainService.predictionOneStockData(predictionParam);
                    return model3PredictSignalAutoProcessor.signalProcessor(trainService.predictionOneStockData(predictionParam),
                            prediction(predictionDataOneStockRes.getFeatures(), modelName), modelName, predictionParam);
                }
        ).reduce(Model3PredictRes::stack).orElseThrow(() -> new IllegalArgumentException("预测集信号叠加异常"));
    }

}
