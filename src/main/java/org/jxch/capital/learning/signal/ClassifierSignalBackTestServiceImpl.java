package org.jxch.capital.learning.signal;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.ClassifierPredictParam;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.learning.classifier.ClassifierModelService;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.jxch.capital.learning.signal.filter.SignalFilters;
import org.jxch.capital.utils.KLineSignals;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierSignalBackTestServiceImpl implements ClassifierSignalBackTestService {
    private final ClassifierModelService classifierModelService;

    @Override
    public List<KLineSignal> backTestByCode(@NonNull SignalBackTestClassifierParam param) {
        List<KLineSignal> kLineSignals = classifierModelService.predict(ClassifierPredictParam.builder()
                .classifierModelId(param.getClassifierModelId())
                .start(param.getStart())
                .code(param.getCode())
                .build());

        KLineSignals.setActionSignal(kLineSignals);
        if (param.hasFilter()) {
            return SignalFilters.chain(SignalFilters.getSignalFilterByName(param.getFilters()), kLineSignals);
        }

        return kLineSignals;
    }

}
