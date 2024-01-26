package org.jxch.capital.learning.model.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.Model3Prediction;
import org.jxch.capital.learning.model.ModelTypeEnum;
import org.jxch.capital.learning.model.Scalers;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.learning.model.dto.TensorflowTFModelMetaData;
import org.jxch.capital.utils.MathU;
import org.jxch.capital.utils.ZipU;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tensorflow.Result;
import org.tensorflow.SavedModelBundle;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.StdArrays;
import org.tensorflow.ndarray.buffer.FloatDataBuffer;
import org.tensorflow.types.TFloat32;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class TensorflowModelPrediction implements Model3Prediction {
    private Map<File, File> modelMap = new ConcurrentHashMap<>();
    private AtomicInteger useModelNum = new AtomicInteger(0);
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Condition condition = lock.writeLock().newCondition();

    @Override
    public boolean support(File modelFile, @NotNull Model3BaseMetaData metaData) {
        if (Objects.equals(ModelTypeEnum.parseOf(metaData.getModeltype()), ModelTypeEnum.TENSORFLOW_MODEL_TF_ZIP)) {
            if (!ZipU.isZipFileByMagic(modelFile)) {
                log.warn("不是有效的ZIP文件，忽略模型: {}", modelFile.toPath());
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    @SneakyThrows
    public File modelPreprocessing(File modelFile, Model3BaseMetaData metaData) {
        try {
            lock.readLock().lock();
            useModelNum.incrementAndGet();
            if (!modelMap.containsKey(modelFile)) {
                modelMap.put(modelFile, ZipU.unZip(modelFile));
            }

            return modelMap.get(modelFile);
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public double[][][] predictionDataPreprocessing(double[][][] x, File modelFile, Model3BaseMetaData metaData) {
        TensorflowTFModelMetaData tfMetaData = (TensorflowTFModelMetaData) metaData;
        if (!tfMetaData.needScaler()) {
            return x;
        }
        return Scalers.transform3ByScalerFile(x, modelFile.toPath().resolve(tfMetaData.getScalername()).toFile());
    }

    @Override
    public double[] prediction(double[][][] x, @NotNull File modelFile, Model3BaseMetaData metaData) {
        TensorflowTFModelMetaData tfMetaData = (TensorflowTFModelMetaData) metaData;
        try (SavedModelBundle modelBundle = SavedModelBundle.load(modelFile.getAbsolutePath(), tfMetaData.getTagArr());
             Session session = modelBundle.session();
             Tensor inputTensor = TFloat32.tensorOf(StdArrays.ndCopyOf(MathU.toFloat3(x)));
             Result run = session.runner().feed(tfMetaData.getInputname(), inputTensor).fetch(tfMetaData.getOutputname()).run();
             Tensor outputTensor = run.get(0)) {

            FloatDataBuffer buffer = outputTensor.asRawTensor().data().asFloats();
            return IntStream.range(0, (int) buffer.size())
                    .mapToDouble(buffer::getFloat)
                    .toArray();
        }
    }

    @Override
    public File modelPostprocessing(File modelFile, Model3BaseMetaData metaData) {
        try {
            lock.writeLock().lock();
            useModelNum.decrementAndGet();
            condition.signalAll();
            return modelFile;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @SneakyThrows
    @Scheduled(cron = "0 0 1 * * ?")
    public void refreshLocal() {
        try {
            lock.writeLock().lock();

            while (useModelNum.get() > 0) {
                condition.await();
            }

            modelMap.forEach((key, value) -> value.deleteOnExit());
            modelMap.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

}
