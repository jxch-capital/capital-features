package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockRes;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictSignalDefaultProcessor implements Model3PredictSignalProcessor {

    @Override
    public boolean support(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        return true;
    }

    @Override
    public Model3PredictRes signalProcessor(PredictionDataOneStockRes predictionDataOneStockRes, @NotNull double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        return customSignalProcessor(Function.identity(), predictionDataOneStockRes, prediction, modelName, predictionParam);
    }

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE;
    }

}
