package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.convert.HistoryParamMapper;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.jxch.capital.yahoo.YahooApi;
import org.jxch.capital.yahoo.dto.DownloadStockCsvParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YahooStockServiceImpl implements StockService {
    private final YahooApi yahooApi;
    private final KLineMapper kLineMapper;
    private final HistoryParamMapper historyParamMapper;

    @Override
    public List<KLine> history(HistoryParam param) {
        DownloadStockCsvParam csvParam = historyParamMapper.toDownloadStockCsvParam(param);
        return kLineMapper.toKLine(yahooApi.downloadStockCsv(csvParam));
    }
}
