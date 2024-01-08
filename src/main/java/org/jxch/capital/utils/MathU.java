package org.jxch.capital.utils;

import com.google.common.math.Stats;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.Arrays;
import java.util.List;

public class MathU {

    public static double[][] transpose(double[][] a) {
        RealMatrix aMatrix = MatrixUtils.createRealMatrix(a);
        RealMatrix aTransposedMatrix = aMatrix.transpose();
        return aTransposedMatrix.getData();
    }

    public static List<Double> normalized(List<Double> values) {
        Stats stats = Stats.of(values);
        return values.stream().map(value -> (value - stats.min()) / (stats.max() - stats.min())).toList();
    }

    public static double[] normalized(double[] values) {
        Stats stats = Stats.of(values);
        return Arrays.stream(values).map(value -> (value - stats.min()) / (stats.max() - stats.min())).toArray();
    }

}
