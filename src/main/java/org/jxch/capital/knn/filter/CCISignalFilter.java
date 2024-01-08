package org.jxch.capital.knn.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.knn.filter.param.CCIFilterParam;
import org.jxch.capital.knn.filter.param.FilterParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CCISignalFilter implements SignalFilter{
    private final CCIDownSignalFilter cciDownSignalFilter;
    private final CCIUpSignalFilter cciUpSignalFilter;

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        cciDownSignalFilter.filter(signals, param);
        cciUpSignalFilter.filter(signals, param);
        return signals;
    }

    @Override
    public FilterParam getDefaultParam() {
        return CCIFilterParam.builder().build();
    }

}
