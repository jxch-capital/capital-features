package org.jxch.capital.learning.classifier;

import lombok.NonNull;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;

public interface ClassifierLearningService {

    ClassifierLearningParam defaultParam();

    default ClassifierLearningParam fit(@NonNull ClassifierLearningParam param) {
        return param.fitClassifier();
    }

    default ClassifierLearningRes predict(@NonNull ClassifierLearningParam param) {
        return ClassifierLearningRes.builder()
                .xP(param.getXP())
                .yP(param.getClassifier().predict(param.getXP()))
                .build();
    }

    default ClassifierLearningRes fitAndPredict(ClassifierLearningParam param) {
        return predict(fit(param));
    }

    default String name() {
        return getClass().getSimpleName();
    }

}
