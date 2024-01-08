package org.jxch.capital.learning;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.dto.LearningParam;
import org.jxch.capital.learning.dto.LearningRes;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AutoLearningServiceImpl implements LearningService {

    @Override
    public LearningParam fit(@NonNull LearningParam param) {
        return param.fitClassifier();
    }

    @Override
    public LearningRes predict(@NonNull LearningParam param) {
        return LearningRes.builder()
                .xP(param.getXP())
                .yP(param.getClassifier().predict(param.getXP()))
                .build();
    }

}
