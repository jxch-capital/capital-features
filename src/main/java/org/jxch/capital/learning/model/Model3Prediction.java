package org.jxch.capital.learning.model;

import org.jxch.capital.learning.model.dto.Model3BaseMetaData;

import java.io.File;

public interface Model3Prediction {

    double[] prediction(double[][][] x, File modelFile, Model3BaseMetaData metaData);


}
