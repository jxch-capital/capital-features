package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class Model3PredictionService implements Model3Prediction {
    @Override
    public boolean support(File modelFile, Model3BaseMetaData metaData) {
        return false;
    }

    public Model3Prediction getSupportModel3Prediction(File modelFile, Model3BaseMetaData metaData) {
        return AppContextHolder.findServiceExcept(Model3Prediction.class, getClass(), service -> service.support(modelFile, metaData));
    }

    @Override
    public File modelPreprocessing(File modelFile, Model3BaseMetaData metaData) {
        return getSupportModel3Prediction(modelFile, metaData).modelPreprocessing(modelFile, metaData);
    }

    @Override
    public double[][][] predictionDataPreprocessing(double[][][] x, File modelFile, Model3BaseMetaData metaData) {
        return getSupportModel3Prediction(modelFile, metaData).predictionDataPreprocessing(x, modelFile, metaData);
    }

    @Override
    public double[] prediction(double[][][] x, File modelFile, Model3BaseMetaData metaData) {
        return getSupportModel3Prediction(modelFile, metaData).prediction(x, modelFile, metaData);
    }

    @Override
    public File modelPostprocessing(File modelFile, Model3BaseMetaData metaData) {
        return getSupportModel3Prediction(modelFile, metaData).modelPostprocessing(modelFile, metaData);
    }

    @Override
    public double[] predictionComplete(double[][][] x, File modelFile, Model3BaseMetaData metaData) {
        return getSupportModel3Prediction(modelFile, metaData).predictionComplete(x, modelFile, metaData);
    }

}
