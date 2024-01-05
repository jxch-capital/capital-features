package org.jxch.capital.filter;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.filter.param.FilterParam;
import org.jxch.capital.server.IndexService;

import java.util.Collections;
import java.util.List;

public interface SignalFilter {

    List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param);

    default List<KLineIndices> index(@NonNull List<KLineSignal> signals, @NonNull FilterParam param, @NonNull IndexService indexService) {
        List<KLine> kLines = signals.stream().map(KLineSignal::getKLine).toList();
        return indexService.index(kLines, param.duration(), Collections.singletonList(param.wrapper()));
    }

    default List<KLineSignal> resetActionSignal(@NonNull ResetSignalFunction resetSignalFunction,
                                                @NonNull List<KLineSignal> signals,
                                                @NonNull FilterParam param,
                                                @NonNull IndexService indexService) {
        List<KLineIndices> kLineIndices = index(signals, param, indexService);

        for (int i = 0; i < signals.size(); i++) {
            if (resetSignalFunction.can(kLineIndices.get(i), signals.get(i).getActionSignal())) {
                signals.get(i).resetActionSignal();
            }
        }

        return signals;
    }

    default String name() {
        return getClass().getSimpleName();
    }

    default FilterParam getDefaultParam() {
        return null;
    }

}
