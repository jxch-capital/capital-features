package org.jxch.capital.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EMADownSignalFilter implements SignalFilter {
    private final IndexService indexService;

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        return resetActionSignal((kLineIndices, actionSignal) ->
                        kLineIndices.getHigh() >= kLineIndices.get(param.name()) && Objects.equals(actionSignal, -1),
                signals, param, indexService);
    }

    @Override
    public FilterParam getDefaultParam() {
        return EMAFilterParam.builder().build();
    }

}
