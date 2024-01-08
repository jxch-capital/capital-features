package org.jxch.capital.knn.filter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.knn.filter.param.DojiFilterParam;
import org.jxch.capital.knn.filter.param.FilterParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DojiSignalFilter implements SignalFilter {

    @Override
    public List<KLineSignal> filter(@NonNull List<KLineSignal> signals, FilterParam param) {
        signals.forEach(kLineSignal -> {
            KLine kLine = kLineSignal.getKLine();
            if ((Math.abs(kLine.getOpen() - kLine.getClose())) / (kLine.getHigh() - kLine.getLow())
                    < ((DojiFilterParam) param).getPercent()) {
                kLineSignal.resetActionSignal();
            }
        });
        return signals;
    }

    @Override
    public FilterParam getDefaultParam() {
        return DojiFilterParam.builder().build();
    }

}
