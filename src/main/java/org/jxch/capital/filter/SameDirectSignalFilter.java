package org.jxch.capital.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;

import java.util.List;

@Slf4j
//@Service
// todo 重构KLineSignal里的信号逻辑，actionSignal 同向信号的实现，应该交给过滤器
@RequiredArgsConstructor
public class SameDirectSignalFilter implements SignalFilter{

    @Override
    public List<KLineSignal> filter(List<KLineSignal> signals, FilterParam param) {
        return null;
    }

}
