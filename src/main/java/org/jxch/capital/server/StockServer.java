package org.jxch.capital.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.HistoryParamMapper;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.yahoo.YahooApi;
import org.jxch.capital.yahoo.dto.DownloadStockCsvParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServer {
    private final YahooApi yahooApi;
    private final KLineMapper kLineMapper;
    private final HistoryParamMapper historyParamMapper;

    public List<KLine> history(HistoryParam param) {
        DownloadStockCsvParam csvParam = historyParamMapper.toDownloadStockCsvParam(param);
        return kLineMapper.toKLine(yahooApi.downloadStockCsv(csvParam));
    }



}
