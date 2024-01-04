package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNNSignalBackTestParam;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.SignalBackTestParam;

import java.util.List;

public interface SignalBackTestService<T extends SignalBackTestParam> {

    List<KLineSignal> backTestByCode(T param);

    Integer signal(KNode kNode, KNNSignalBackTestParam param);

}
