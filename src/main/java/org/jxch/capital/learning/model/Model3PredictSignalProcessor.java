package org.jxch.capital.learning.model;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.dto.KLineModelSignal;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.model.dto.PredictSignalStack;
import org.jxch.capital.learning.model.dto.PredictionParam;
import org.jxch.capital.learning.train.TrainDataRes;
import org.springframework.core.Ordered;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public interface Model3PredictSignalProcessor extends Ordered {

    boolean support(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam);


    Model3PredictRes signalProcessor(TrainDataRes trainDataRes, double[] prediction, String modelName, PredictionParam predictionParam);

    default Model3PredictRes customSignalProcessor(Function<Double, Double> custom, TrainDataRes trainDataRes,
                                                   @NotNull double[] prediction, String modelName, PredictionParam predictionParam) {
        List<KLineModelSignal> kLineModelSignals = IntStream.range(0, prediction.length).mapToObj(index ->
                KLineModelSignal.builder()
                        .kLine(trainDataRes.getSourceKLines().get(index))
                        .predictSignalStack(PredictSignalStack.of(modelName, custom.apply(prediction[index])))
                        .build()).toList();

        return Model3PredictRes.builder()
                .predictionParam(predictionParam)
                .kLineModelSignals(kLineModelSignals).build();
    }

    @Override
    default int getOrder() {
        return 0;
    }

}
