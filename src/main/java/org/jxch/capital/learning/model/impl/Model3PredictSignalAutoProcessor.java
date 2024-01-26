package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.model.dto.PredictionParam;
import org.jxch.capital.learning.train.TrainDataRes;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictSignalAutoProcessor implements Model3PredictSignalProcessor {

    @Override
    public boolean support(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam) {
        return false;
    }

    public Model3PredictSignalProcessor getSupportProcessor(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam) {
        return AppContextHolder.findOrderedFristServiceExcept(Model3PredictSignalProcessor.class, getClass(), service -> service.support(trainDataRes, prediction, modelName, predictionParam));
    }

    @Override
    public Model3PredictRes signalProcessor(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam) {
        return getSupportProcessor(trainDataRes, prediction, modelName, predictionParam)
                .signalProcessor(trainDataRes, prediction, modelName, predictionParam);
    }

}
