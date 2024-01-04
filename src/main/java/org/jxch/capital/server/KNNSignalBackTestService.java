package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNNSignalBackTestParam;

import java.util.List;

public interface KNNSignalBackTestService extends SignalBackTestService<KNNSignalBackTestParam> {

    @Override
    List<KLineSignal> backTestByCode(KNNSignalBackTestParam signalBackTestParam);

}
