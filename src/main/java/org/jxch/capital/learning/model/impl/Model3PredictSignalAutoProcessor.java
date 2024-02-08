package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictSignalAutoProcessor implements Model3PredictSignalProcessor {

    @Override
    public boolean support(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        return false;
    }

    public Model3PredictSignalProcessor getSupportProcessor(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        return AppContextHolder.findOrderedFristServiceExcept(Model3PredictSignalProcessor.class, getClass(), service -> service.support(predictionDataOneStockRes, prediction, modelName, predictionParam));
    }

    @Override
    public Model3PredictRes signalProcessor(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        return getSupportProcessor(predictionDataOneStockRes, prediction, modelName, predictionParam)
                .signalProcessor(predictionDataOneStockRes, prediction, modelName, predictionParam);
    }

}
