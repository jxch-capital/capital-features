package org.jxch.capital.learning;

import org.jxch.capital.learning.dto.LearningParam;
import org.jxch.capital.learning.dto.LearningRes;

public interface LearningService {

    LearningParam fit(LearningParam param);

    LearningRes predict(LearningParam param);

    default LearningRes fitAndPredict(LearningParam param) {
        return predict(fit(param));
    }

}
