package org.jxch.capital.learning;

import lombok.NonNull;
import org.jxch.capital.learning.dto.LearningParam;
import org.jxch.capital.utils.KNodes;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

public class Learnings {

    public static LearningParam SVMGaussianKernel() {
        return LearningParam.defaultParam()
                .setKernel(new GaussianKernel(2.0))
                .setClassifierFitFunc(param -> SVM.fit(param.getXT(), param.getYT(), param.getC(), param.getTol(), param.getEpochs()));
    }

    public static LearningParam toLearningParamByKLineH(@NonNull LearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrH(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrH(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

    public static LearningParam toLearningParamByKLineV(@NonNull LearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrV(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrV(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

}
