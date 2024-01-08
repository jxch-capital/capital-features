package org.jxch.capital.knn.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.knn.filter.param.FilterParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectionSameSignalFilter implements SignalFilter {

    @Override
    public List<KLineSignal> filter(@NonNull List<KLineSignal> signals, FilterParam param) {
        signals.forEach(kLineSignal -> {
            if (kLineSignal.getTureSignal() * kLineSignal.getSignal() < 0) {
                kLineSignal.resetActionSignal();
            }
        });
        return signals;
    }

}
