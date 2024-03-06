package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.old.train.param.dto.PredictionOneStockParam;

import java.util.List;

public interface Model3PredictionCompleteService {

    double[] prediction(double[][][] data, String modelName);

    double[] prediction(String modelName, PredictionOneStockParam predictionParam);

    Model3PredictRes predictionCarry(String modelName, PredictionOneStockParam predictionParam);

    Model3PredictRes predictionCarry(List<String> modelNames, PredictionOneStockParam predictionParam);

}
