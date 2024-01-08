package org.jxch.capital.learning.classifier;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AutoClassifierLearningServiceImpl implements ClassifierLearningService {

    @Override
    public ClassifierLearningParam fit(@NonNull ClassifierLearningParam param) {
        return param.fitClassifier();
    }

    @Override
    public ClassifierLearningRes predict(@NonNull ClassifierLearningParam param) {
        return ClassifierLearningRes.builder()
                .xP(param.getXP())
                .yP(param.getClassifier().predict(param.getXP()))
                .build();
    }

}
