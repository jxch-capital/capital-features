package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KDashNode;
import org.jxch.capital.domain.dto.StockBaseDto;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockBaseService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.stock.StockService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RealPricePoolDashboardServiceImpl implements RealPricePoolDashboardService {
    private final StockService stockService;
    private final StockBaseService stockBaseService;
    private final StockPoolService stockPoolService;

    @Override
    @SneakyThrows
    public List<KDashNode> realDashNodes(Long stockPoolId, Date start) {
        StockPoolDto stockPoolDto = stockPoolService.findById(stockPoolId);
        Map<String, StockBaseDto> stockBaseDtoMap = stockBaseService.findByCode(stockPoolDto.getPoolStockList()).stream()
                .collect(Collectors.toMap(StockBaseDto::getCode, Function.identity()));
        return new ForkJoinPool(Runtime.getRuntime().availableProcessors())
                .submit(() -> stockPoolDto.getPoolStockList().parallelStream()
                        .map(code -> KDashNode.builder()
                                .code(code)
                                .name((Optional.ofNullable(stockBaseDtoMap.get(code)).map(StockBaseDto::getName).orElse(code)))
                                .kLines(stockService.history1d(HistoryParam.builder()
                                        .code(code)
                                        .interval(stockPoolDto.getInterval())
                                        .engine(stockPoolDto.getEngineEnum())
                                        .start(start)
                                        .build()))
                                .build())
                        .sorted(Comparator.comparing(KDashNode::getName)).toList())
                .get();
    }

}
