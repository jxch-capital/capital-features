package org.jxch.capital.learning.signal;

import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.learning.signal.dto.SignalBackTestKNNParam;

import java.util.List;

public interface KNNSignalBackTestService extends SignalBackTestService<SignalBackTestKNNParam> {

    @Override
    List<KLineSignal> backTestByCode(SignalBackTestKNNParam signalBackTestParam);

}
