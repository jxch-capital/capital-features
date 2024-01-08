package org.jxch.capital.learning.classifier;

import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;

public interface ClassifierLearningService {

    ClassifierLearningParam fit(ClassifierLearningParam param);

    ClassifierLearningRes predict(ClassifierLearningParam param);

    default ClassifierLearningRes fitAndPredict(ClassifierLearningParam param) {
        return predict(fit(param));
    }

}
