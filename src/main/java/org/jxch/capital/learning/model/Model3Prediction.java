package org.jxch.capital.learning.model;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.utils.AppContextHolder;

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

    default double[] predictionComplete(double[][][] features, String modelName, @NotNull Model3Management model3Management) {
        Model3BaseMetaData modelMetaData = model3Management.findModelMetaData(modelName);
        return predictionComplete(features, model3Management.getModelFile(modelName), modelMetaData);
    }

    default double[] predictionComplete(double[][][] features, String modelName) {
        return predictionComplete(features, modelName, AppContextHolder.getContext().getBean(Model3Management.class));
    }

}
