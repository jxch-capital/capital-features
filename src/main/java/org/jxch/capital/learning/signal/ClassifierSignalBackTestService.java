package org.jxch.capital.learning.signal;

import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.jxch.capital.learning.signal.dto.SignalBackTestKNNParam;

public interface ClassifierSignalBackTestService extends SignalBackTestService<SignalBackTestClassifierParam> {

    default Integer signal(KNode kNode, SignalBackTestKNNParam param) {
        throw new UnsupportedOperationException("暂不支持");
    }

}
