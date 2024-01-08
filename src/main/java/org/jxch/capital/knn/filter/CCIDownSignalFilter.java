package org.jxch.capital.knn.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.knn.filter.param.CCIFilterParam;
import org.jxch.capital.knn.filter.param.FilterParam;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CCIDownSignalFilter implements SignalFilter {
    private final IndexService indexService;

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        return resetActionSignal((k, s) -> k.get(param.name()) < -((CCIFilterParam) param).getLimitAbs(), signals, param, indexService);
    }

    @Override
    public FilterParam getDefaultParam() {
        return CCIFilterParam.builder().build();
    }

}
