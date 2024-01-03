package org.jxch.capital.nn;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class DL4JTest {

    @Test
    public void run() {
        int numInputs = 40 * 4; // 输入层的神经元数目
        int numOutputs = 1;     // 输出层的神经元数目，二分类问题设为2
        int numChannels = 1;    // 通道数，灰度值（非RGB）

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .updater(new Adam(0.01)) // 使用Adam优化器
                .weightInit(WeightInit.XAVIER) // 使用Xavier权重初始化
                .list()
                .layer(new ConvolutionLayer.Builder(5, 5)
                        .nIn(numChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.RELU)
                        .build())
                .layer(new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                // 添加其他该有的卷积层/池化层
                .layer(new DenseLayer.Builder().activation(Activation.RELU) // 添加全连接层
                        .nIn(800)
                        .nOut(500).build())
                .layer(new OutputLayer.Builder(LossFunctions.LossFunction.XENT) // Use XENT for binary cross-entropy
                        .nIn(1)
                        .nOut(numOutputs) // numOutputs: 对于二分类问题，这个值应该设置为1
                        .activation(Activation.SIGMOID) // Use SIGMOID activation for binary classification
                        .build())
                .build();

        // 构建模型
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        model.setListeners(new ScoreIterationListener(100)); // 每隔100个样本输出一次分数

        // 加载并准备数据…
        // 训练模型
        // int nEpochs = 10; // 定义迭代次数
        // for (int i = 0; i < nEpochs; i++) {
        //     model.fit(trainingData); // 使用你的训练数据去拟合模型
        // }

        // 评估模型…
    }

}
