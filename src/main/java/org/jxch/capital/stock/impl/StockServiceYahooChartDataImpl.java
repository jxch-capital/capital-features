package org.jxch.capital.stock.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.HistoryParamMapper;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.http.yahoo.YahooApi;
import org.jxch.capital.stock.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceYahooChartDataImpl implements StockService {
    private final YahooApi yahooApi;
    private final KLineMapper kLineMapper;
    private final HistoryParamMapper historyParamMapper;

    @Override
    public List<KLine> history(HistoryParam param) {
        return kLineMapper.toKLineByYahooChartRes(yahooApi.chart(historyParamMapper.toChartParam(param)));
    }

}
