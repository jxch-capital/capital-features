package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3BaseMetaData;

import java.io.File;

public interface Model3Prediction {

    boolean support(File modelFile, Model3BaseMetaData metaData);

    File modelPreprocessing(File modelFile, Model3BaseMetaData metaData);

    double[][][] predictionDataPreprocessing(double[][][] x, File modelFile, Model3BaseMetaData metaData);

    double[] prediction(double[][][] x, File modelFile, Model3BaseMetaData metaData);

    File modelPostprocessing(File modelFile, Model3BaseMetaData metaData);

    default double[] predictionComplete(double[][][] x, File modelFile, Model3BaseMetaData metaData) {
        if (!support(modelFile, metaData)) {
            throw new IllegalArgumentException("不支持的模型: " + modelFile.toPath());
        }

        File newModelFile = modelPreprocessing(modelFile, metaData);
        double[][][] newData = predictionDataPreprocessing(x, newModelFile, metaData);
        double[] prediction = prediction(newData, newModelFile, metaData);
        modelPostprocessing(newModelFile, metaData);
        return prediction;
    }

}
