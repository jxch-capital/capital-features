package org.jxch.capital.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.filter.param.EMAFilterParam;
import org.jxch.capital.filter.param.FilterParam;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EMAUpSignalFilter implements SignalFilter {
    private final IndexService indexService;

    @Override
    public List<KLineSignal> filter(@NonNull List<KLineSignal> signals, @NonNull FilterParam param) {
        return resetActionSignal((kLineIndices, actionSignal) ->
                        kLineIndices.getLow() < kLineIndices.get(param.name()) && Objects.equals(actionSignal, 1),
                signals, param, indexService);
    }

    @Override
    public FilterParam getDefaultParam() {
        return EMAFilterParam.builder().build();
    }

}
