package org.jxch.capital.stock.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.convert.HistoryParamMapper;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.http.yahoo.YahooApi;
import org.jxch.capital.http.yahoo.dto.DownloadStockCsvParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceYahooCsvImpl implements StockService {
    private final YahooApi yahooApi;
    private final KLineMapper kLineMapper;
    private final HistoryParamMapper historyParamMapper;

    @Override
    public List<KLine> history(HistoryParam param) {
        DownloadStockCsvParam csvParam = historyParamMapper.toDownloadStockCsvParam(param);
        return kLineMapper.toKLine(yahooApi.downloadStockCsv(csvParam));
    }
}
