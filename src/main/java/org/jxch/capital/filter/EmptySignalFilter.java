package org.jxch.capital.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.filter.param.FilterParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmptySignalFilter implements SignalFilter{
    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        return signals;
    }

    @Override
    public String name() {
        return "";
    }

}
