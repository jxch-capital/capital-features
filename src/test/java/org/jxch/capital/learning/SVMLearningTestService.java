package org.jxch.capital.learning;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

@Slf4j
class SVMLearningTestService {

    @Test
    public void text() {
        // 假设你有一些K线数据，这里我们只是用随机数代替
        double[][] x = new double[100][2];
        int[] y = new int[100];
        for (int i = 0; i < x.length; i++) {
            x[i][0] = Math.random();
            x[i][1] = Math.random();
            y[i] = (x[i][0] > x[i][1]) ? 1 : -1;
        }

        // 创建一个高斯核函数
        GaussianKernel kernel = new GaussianKernel(2.0);

        // 创建一个SVM分类器
        SVM<double[]> svm = SVM.fit(x, y, kernel, 1.0, 1E-3,100);

        // 使用SVM分类器进行预测
        int[] prediction = new int[x.length];
        for (int i = 0; i < x.length; i++) {
            prediction[i] = svm.predict(x[i]);
        }

        log.info(JSONObject.toJSONString(prediction));
    }

}