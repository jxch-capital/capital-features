package org.jxch.capital.knn;

import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNNSignalBackTestParam;
import org.jxch.capital.server.SignalBackTestService;

import java.util.List;

public interface KNNSignalBackTestService extends SignalBackTestService<KNNSignalBackTestParam> {

    @Override
    List<KLineSignal> backTestByCode(KNNSignalBackTestParam signalBackTestParam);

}
