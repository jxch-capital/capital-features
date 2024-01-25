package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3MetaData;

import java.io.File;

public interface Model3Prediction {

    double[] prediction(double[][][] x, File modelFile, Model3MetaData metaData);


}
