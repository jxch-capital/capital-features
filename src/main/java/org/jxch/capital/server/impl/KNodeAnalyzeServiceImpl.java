package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNodeAnalyzeService;
import org.jxch.capital.server.StockHistoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNodeAnalyzeServiceImpl implements KNodeAnalyzeService {
    private final StockHistoryService stockHistoryService;
    private final KLineMapper kLineMapper;

    @Override
    public List<KLine> search(Long stockPoolId, String stockCode, Date startDate, Date endDate) {
        return kLineMapper.toKLineByStockHistoryDto(stockHistoryService.findByStockPoolIdAndStockCode(stockPoolId, stockCode, startDate, endDate));
    }

}
