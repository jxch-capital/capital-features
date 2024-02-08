package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.param.PredictionDataParam;
import org.jxch.capital.learning.train.param.PredictionDataRes;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictSignalAutoProcessor implements Model3PredictSignalProcessor {

    @Override
    public boolean support(PredictionDataRes predictionDataRes, double[] prediction, String modelName, PredictionDataParam predictionParam) {
        return false;
    }

    public Model3PredictSignalProcessor getSupportProcessor(PredictionDataRes predictionDataRes, double[] prediction, String modelName, PredictionDataParam predictionParam) {
        return AppContextHolder.findOrderedFristServiceExcept(Model3PredictSignalProcessor.class, getClass(), service -> service.support(predictionDataRes, prediction, modelName, predictionParam));
    }

    @Override
    public Model3PredictRes signalProcessor(PredictionDataRes predictionDataRes, double[] prediction, String modelName, PredictionDataParam predictionParam) {
        return getSupportProcessor(predictionDataRes, prediction, modelName, predictionParam)
                .signalProcessor(predictionDataRes, prediction, modelName, predictionParam);
    }

}
