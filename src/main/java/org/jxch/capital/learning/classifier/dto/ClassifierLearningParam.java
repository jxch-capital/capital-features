package org.jxch.capital.learning.classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import smile.classification.Classifier;
import smile.math.kernel.MercerKernel;

import java.util.List;
import java.util.function.Function;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierLearningParam {
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

    public ClassifierLearningParam toLearningParamByKLineH() {
        return ClassifierLearnings.toLearningParamByKLineH(this);
    }

    public ClassifierLearningParam toLearningParamByKLineV() {
        return ClassifierLearnings.toLearningParamByKLineV(this);
    }

}
