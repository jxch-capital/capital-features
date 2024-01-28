package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.model.dto.PredictionParam;
import org.jxch.capital.learning.train.param.TrainDataRes;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictSignalDefaultProcessor implements Model3PredictSignalProcessor {

    @Override
    public boolean support(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam) {
        return true;
    }

    @Override
    public Model3PredictRes signalProcessor(TrainDataRes trainDataRes, @NotNull double[] prediction, String modelName, PredictionParam predictionParam) {
        return customSignalProcessor(Function.identity(), trainDataRes, prediction, modelName, predictionParam);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
