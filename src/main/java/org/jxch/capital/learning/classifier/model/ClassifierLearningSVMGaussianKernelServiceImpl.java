package org.jxch.capital.learning.classifier.model;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.springframework.stereotype.Service;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

@Slf4j
@Service
public class ClassifierLearningSVMGaussianKernelServiceImpl implements ClassifierLearningService {

    @Override
    public ClassifierLearningParam defaultParam() {
        return ClassifierLearningParam.defaultParam()
                .setKernel(new GaussianKernel(2.0))
                .setClassifierFitFunc(param -> SVM.fit(param.getXT(), param.getYT(), param.getC(), param.getTol(), param.getEpochs()))
                .setDataFunc(ClassifierLearningParam::setAllDataByKLineH)
                .setModelName(name());
    }

    @Override
    public String name() {
        return "SVM+GaussianKernel-H";
    }

}
