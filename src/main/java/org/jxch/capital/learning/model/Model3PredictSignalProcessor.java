package org.jxch.capital.learning.model;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.dto.KLineModelSignal;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.model.dto.PredictSignalStack;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockRes;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public interface Model3PredictSignalProcessor extends Ordered {

    boolean support(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam);


    Model3PredictRes signalProcessor(PredictionDataOneStockRes predictionDataOneStockRes, double[] prediction, String modelName, PredictionDataOneStockParam predictionParam);

    default Model3PredictRes customSignalProcessor(Function<Double, Double> custom, PredictionDataOneStockRes predictionDataOneStockRes,
                                                   @NotNull double[] prediction, String modelName, PredictionDataOneStockParam predictionParam) {
        List<KLineModelSignal> kLineModelSignals = IntStream.range(0, prediction.length).mapToObj(index ->
                KLineModelSignal.builder()
                        .kLine(predictionDataOneStockRes.getSourceKLines().get(index))
                        .predictSignalStack(PredictSignalStack.of(modelName, custom.apply(prediction[index])))
                        .build()).toList();

        return Model3PredictRes.builder()
                .predictionDataOneStockParam(predictionParam)
                .kLineModelSignals(kLineModelSignals).build();
    }

    @Override
    default int getOrder() {
        return 0;
    }

}
