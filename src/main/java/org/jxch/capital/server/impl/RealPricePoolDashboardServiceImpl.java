package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockBaseService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.server.StockService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealPricePoolDashboardServiceImpl implements RealPricePoolDashboardService {
    private final StockService stockService;
    private final StockBaseService stockBaseService;
    private final StockPoolService stockPoolService;

    @Override
    public List<KDashNode> realDashNodes(Long stockPoolId, Date start) {
        StockPoolDto stockPoolDto = stockPoolService.findById(stockPoolId);
        return stockPoolDto.getPoolStockList().stream().map(code -> {
            List<KLine> history = stockService.history(HistoryParam.builder()
                    .code(code)
                    .interval(stockPoolDto.getInterval())
                    .engine(stockPoolDto.getEngineEnum())
                    .start(start)
                    .build());
            StockBaseDto stockBase = stockBaseService.findByCode(code);

            return KDashNode.builder()
                    .kLines(history)
                    .code(code)
                    .name(Objects.nonNull(stockBase) ? stockBase.getName() : code)
                    .build();
        }).toList();
    }

}
