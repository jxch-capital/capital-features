package org.jxch.capital.learning.model;

import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.dto.StandardScaler;

import java.io.File;
import java.nio.file.Files;

public class Scalers {
    @SneakyThrows
    public static StandardScaler scalerByJsonFile(@NotNull File scalerJsonFile) {
        return JSONObject.parseObject(new String(Files.readAllBytes(scalerJsonFile.toPath())), StandardScaler.class);
    }

    @NotNull
    public static double[][] transform2(@NotNull double[][][] data, @NotNull StandardScaler scaler) {
        double[] mean = scaler.getMean();
        double[] var = scaler.getVar();
        // 计算第二维和第三维的长度
        int numTimesteps = data[0].length;
        int numFeatures = data[0][0].length;

// 重塑和标准化数据
        double[][] reshapedData = new double[data.length * numTimesteps][numFeatures];
        for (int i = 0; i < data.length; i++) {
            for (int t = 0; t < numTimesteps; t++) {
                for (int f = 0; f < numFeatures; f++) {
                    int index = i * numTimesteps + t;
                    reshapedData[index][f] = (data[i][t][f] - mean[f]) / Math.sqrt(var[f]);
                }
            }
        }

        return reshapedData;
    }

    @NotNull
    public static double[][][] transform3(@NotNull double[][][] data, StandardScaler scaler) {
        int numTimesteps = data[0].length;
        int numFeatures = data[0][0].length;
        double[][] reshapedData = transform2(data, scaler);

// 因为在大多数场景中，模型会期望二维数组进行预测。如果确实需要三维数组，
// 我们可以在标准化后的步骤中将数据重新转换回去。
        double[][][] scaledData = new double[data.length][numTimesteps][numFeatures];
        for (int i = 0; i < data.length; i++) {
            for (int t = 0; t < numTimesteps; t++) {
                System.arraycopy(reshapedData[i * numTimesteps + t], 0, scaledData[i][t], 0, numFeatures);
            }
        }

        return scaledData;
    }

    @NotNull
    public static double[][][] transform3ByScalerFile(@NotNull double[][][] data, @NotNull File scalerJsonFile) {
        return transform3(data, scalerByJsonFile(scalerJsonFile));
    }

}
