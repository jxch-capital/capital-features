package org.jxch.capital.learning.signal.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.learning.signal.filter.param.CCIFilterParam;
import org.jxch.capital.learning.signal.filter.param.FilterParam;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CCIUpSignalFilter implements SignalFilter {
    private final IndexService indexService;

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        return resetActionSignal((k, s) -> k.get(param.name()) > ((CCIFilterParam) param).getLimitAbs(), signals, param, indexService);
    }

    @Override
    public FilterParam getDefaultParam() {
        return CCIFilterParam.builder().build();
    }

}
