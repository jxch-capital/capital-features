package org.jxch.capital.learning.classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.utils.KNodes;
import smile.classification.Classifier;
import smile.math.kernel.MercerKernel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierLearningParam { // todo 继承接口强制类型转换，以适应不同的分类器
    @Builder.Default
    private double[][] xT = null;
    @Builder.Default
    private int[] yT = null;
    @Builder.Default
    private MercerKernel<double[]> kernel = null;
    @Builder.Default
    private Classifier<double[]> classifier = null;
    private double c;
    private double tol;
    private int epochs;
    @Builder.Default
    private Function<ClassifierLearningParam, Classifier<double[]>> classifierFitFunc = null;
    @Builder.Default
    private double[][] xP = null;
    @Builder.Default
    private int futureNum = 6;
    @Builder.Default
    private int size = 20;
    @Builder.Default
    private List<KNode> kNodesT = null;
    @Builder.Default
    private List<KNode> kNodesP = null;
    @Builder.Default
    private Consumer<ClassifierLearningParam> dataFunc = null;
    @Builder.Default
    private String modelName = null;


    public ClassifierLearningParam fitClassifier() {
        classifier = classifierFitFunc.apply(this);
        return this;
    }

    public static ClassifierLearningParam defaultParam() {
        return ClassifierLearningParam.builder()
                .c(1.0)
                .tol(1E-3)
                .epochs(10)
                .build();
    }

    public ClassifierLearningParam setTDataByKLineH() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedKArrH(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

    public ClassifierLearningParam setPDataByKLineH() {
        return this.setXP(KNodes.normalizedKArrH(KNodes.sliceLastFuture(this.getKNodesP(), this.getFutureNum(), this.getSize())));
    }

    public ClassifierLearningParam setAllDataByKLineH() {
        return this.setTDataByKLineH().setPDataByKLineH();
    }

    public ClassifierLearningParam setTDataByKLineV() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedKArrV(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

    public ClassifierLearningParam setPDataByKLineV() {
        return this.setXP(KNodes.normalizedKArrV(KNodes.sliceLastFuture(this.getKNodesP(), this.getFutureNum(), this.getSize())));
    }

    public ClassifierLearningParam setAllDataByKLineV() {
        return this.setTDataByKLineV().setPDataByKLineV();
    }

}
