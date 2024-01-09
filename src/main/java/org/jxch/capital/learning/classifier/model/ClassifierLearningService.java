package org.jxch.capital.learning.classifier.model;

import lombok.NonNull;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;

public interface ClassifierLearningService {

    ClassifierLearningParam defaultParam();

    default ClassifierLearningParam fit(@NonNull ClassifierLearningParam param) {
        param.getDataFunc().accept(param);
        return param.fitClassifier();
    }

    default ClassifierLearningRes predict(@NonNull ClassifierLearningParam param) {
        return ClassifierLearningRes.builder()
                .xP(param.getXP())
                .yP(param.getClassifier().predict(param.getXP()))
                .build();
    }

    default ClassifierLearningRes fitAndPredict(@NonNull ClassifierLearningParam param) {
        return predict(fit(param));
    }

    default String name() {
        return getClass().getSimpleName();
    }

    default ClassifierLearningParam save(@NonNull ClassifierLearningParam param) {
        ClassifierLearnings.save(param.getClassifier(), param.getModelName());
        return param;
    }

    default ClassifierLearningParam load(@NonNull ClassifierLearningParam param) {
        return param.setClassifier(ClassifierLearnings.load(param.getModelName()));
    }

    default ClassifierLearningParam fitAndSave(@NonNull ClassifierLearningParam param) {
        return save(fit(param));
    }

}
