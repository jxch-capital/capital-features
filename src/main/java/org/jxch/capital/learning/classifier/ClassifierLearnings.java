package org.jxch.capital.learning.classifier;

import lombok.NonNull;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.utils.KNodes;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

public class ClassifierLearnings {

    public static ClassifierLearningParam SVMGaussianKernel() {
        return ClassifierLearningParam.defaultParam()
                .setKernel(new GaussianKernel(2.0))
                .setClassifierFitFunc(param -> SVM.fit(param.getXT(), param.getYT(), param.getC(), param.getTol(), param.getEpochs()));
    }

    public static ClassifierLearningParam toLearningParamByKLineH(@NonNull ClassifierLearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrH(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrH(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

    public static ClassifierLearningParam toLearningParamByKLineV(@NonNull ClassifierLearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrV(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrV(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

}
