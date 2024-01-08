package org.jxch.capital.learning.signal.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.learning.signal.filter.param.EMAFilterParam;
import org.jxch.capital.learning.signal.filter.param.FilterParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EMASignalFilter implements SignalFilter {
    private final EMAUpSignalFilter emaUpSignalFilter;
    private final EMADownSignalFilter emaDownSignalFilter;

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        emaUpSignalFilter.filter(signals, param);
        emaDownSignalFilter.filter(signals, param);
        return signals;
    }

    @Override
    public FilterParam getDefaultParam() {
        return EMAFilterParam.builder().build();
    }

}
