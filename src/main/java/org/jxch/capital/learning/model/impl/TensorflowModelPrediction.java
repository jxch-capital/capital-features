package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.model.dto.Model3MetaData;
import org.springframework.stereotype.Service;
import org.tensorflow.Result;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.StdArrays;
import org.tensorflow.ndarray.buffer.FloatDataBuffer;
import org.tensorflow.types.TFloat32;

import java.io.File;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TensorflowModelPrediction implements Model3Prediction {
    private final TensorflowModel3Management tensorflowModel3Management;

    @Override
    public double[] prediction(double[][] x, @NotNull File modelFile, Model3MetaData metaData) {
        try (SavedModelBundle modelBundle = SavedModelBundle.load(modelFile.getAbsolutePath(), "serve");
             Session session = modelBundle.session();
             Tensor inputTensor = TFloat32.tensorOf(StdArrays.ndCopyOf(x).shape());
             Result run = session.runner().feed(metaData.getInputname(), inputTensor).fetch(metaData.getOutputname()).run();
             Tensor outputTensor = run.get(0)) {

            FloatDataBuffer buffer = outputTensor.asRawTensor().data().asFloats();
            return IntStream.range(0, (int) buffer.size())
                    .mapToDouble(buffer::getFloat)
                    .toArray();
        }
    }

}
