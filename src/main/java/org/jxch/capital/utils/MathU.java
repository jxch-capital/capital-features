package org.jxch.capital.utils;

import com.google.common.math.Stats;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.ejml.simple.SimpleMatrix;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    public static Float[][][] toFloat3(double[][][] data) {
        return Arrays.stream(data).map(a -> Arrays.stream(a).map(b -> Arrays.stream(b).mapToObj(c -> (float) c)
                .toArray(Float[]::new)).toArray(Float[][]::new)).toArray(Float[][][]::new);
    }

    public static boolean hasInvalid3(double[][][] array) {
        return Arrays.stream(array).flatMap(Arrays::stream).flatMapToDouble(Arrays::stream).parallel()
                .anyMatch(value -> Double.isNaN(value) || Double.isInfinite(value));
    }

    public static boolean hasInvalid2(double[][] array) {
        return Arrays.stream(array).flatMapToDouble(Arrays::stream).parallel()
                .anyMatch(value -> Double.isNaN(value) || Double.isInfinite(value));
    }

    public static boolean hasNan3(double[][][] array) {
        return Arrays.stream(array).flatMap(Arrays::stream).flatMapToDouble(Arrays::stream).parallel()
                .anyMatch(Double::isNaN);
    }

    public static boolean hasInf3(double[][][] array) {
        return Arrays.stream(array).flatMap(Arrays::stream).flatMapToDouble(Arrays::stream).parallel()
                .anyMatch(Double::isInfinite);
    }

    public static  double[][] toT(double[][] t) {
        return new SimpleMatrix(t).transpose().toArray2();
    }

}
