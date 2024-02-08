package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3Management;
import org.jxch.capital.learning.model.Model3PredictSignalProcessor;
import org.jxch.capital.learning.model.PredictSignalTypeEnum;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.param.PredictionDataParam;
import org.jxch.capital.learning.train.param.PredictionDataRes;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class Model3PredictFollowDownSignalProcessor implements Model3PredictSignalProcessor {
    private final Model3Management model3Management;

    @Override
    public boolean support(PredictionDataRes predictionDataRes, double[] prediction, String modelName, PredictionDataParam predictionParam) {
        Model3BaseMetaData modelMetaData = model3Management.findModelMetaData(modelName);
        return Objects.equals(PredictSignalTypeEnum.parseOf(modelMetaData.getPredictsignaltype()), PredictSignalTypeEnum.FOLLOW_DOWN);
    }

    @Override
    public Model3PredictRes signalProcessor(PredictionDataRes predictionDataRes, double[] prediction, String modelName, PredictionDataParam predictionParam) {
        return customSignalProcessor(pred -> pred > 0.6 ? -pred + 0.5 : 0, predictionDataRes, prediction, modelName, predictionParam);
    }

}
