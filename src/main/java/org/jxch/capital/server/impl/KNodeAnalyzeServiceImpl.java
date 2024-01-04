package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineAnalyzeStatistics;
import org.jxch.capital.domain.dto.KLineAnalyzedParam;
import org.jxch.capital.domain.dto.KLineAnalyzes;
import org.jxch.capital.server.KNodeAnalyzeService;
import org.jxch.capital.server.StockHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNodeAnalyzeServiceImpl implements KNodeAnalyzeService {
    private final StockHistoryService stockHistoryService;
    private final KLineMapper kLineMapper;

    @Override
    public List<KLine> search(@NonNull KLineAnalyzedParam param) {
        return kLineMapper.toKLineByStockHistoryDto(stockHistoryService.findByStockPoolIdAndStockCode(
                param.getStockPoolId(), param.getStockCode(), param.getExtendStartDate(), param.getExtendEndDate()));
    }

    private KLineAnalyzes buildKLineAnalyzes(@NonNull KLineAnalyzedParam param) {
        return KLineAnalyzes.builder()
                .code(param.getStockCode())
                .startDate(param.getStartDate())
                .endDate(param.getEndDate())
                .futureNum(param.getFutureNum())
                .build();
    }

    @Override
    public KLineAnalyzes analyze(@NonNull KLineAnalyzedParam param) {
        return buildKLineAnalyzes(param)
                .setKLines(search(param))
                .analyze();
    }

    @Override
    public KLineAnalyzeStatistics statistics(List<KLineAnalyzes> analyzes) {
        return KLineAnalyzeStatistics.builder().analyzes(analyzes).build().statistics();
    }

}
