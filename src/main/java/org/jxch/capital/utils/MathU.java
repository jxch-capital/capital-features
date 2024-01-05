package org.jxch.capital.utils;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MathU {

    public static double[][] transpose(double[][] a) {
        RealMatrix aMatrix = MatrixUtils.createRealMatrix(a);
        RealMatrix aTransposedMatrix = aMatrix.transpose();
        return aTransposedMatrix.getData();
    }

}
