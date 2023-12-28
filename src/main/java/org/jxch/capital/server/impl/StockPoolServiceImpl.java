package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.StockPoolRepository;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.convert.StockPoolMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.StockHistoryDto;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.EngineEnum;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.server.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolServiceImpl implements StockPoolService {
    private final StockPoolRepository stockPoolRepository;
    private final StockPoolMapper stockPoolMapper;
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    private final KLineMapper kLineMapper;

    @Override
    public List<StockPoolDto> findAll() {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findAll());
    }

    @Override
    public StockPoolDto findById(Long id) {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findById(id).orElseThrow());
    }

    @Override
    public Integer save(List<StockPoolDto> stockPools) {
        return stockPoolRepository.saveAllAndFlush(stockPoolMapper.toStockPool(stockPools)).size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void del(List<Long> id) {
        stockPoolRepository.deleteAllById(id);
        stockHistoryService.delByStockPoolId(id);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(@NonNull List<Long> stockPoolIds) {
        for (Long stockPoolId : stockPoolIds) {
            stockHistoryService.delByStockPoolId(Collections.singletonList(stockPoolId));

            StockPoolDto stockPoolDto = findById(stockPoolId);
            stockPoolDto.getPoolStockList().forEach(code -> {
                List<KLine> history = stockService.history(HistoryParam.builder()
                        .code(code)
                        .start(stockPoolDto.getStartDate())
                        .end(stockPoolDto.getEndDate())
                        .interval(stockPoolDto.getInterval())
                        .engine(EngineEnum.pares(stockPoolDto.getEngine()))
                        .build());
                List<StockHistoryDto> stockHistoryList = kLineMapper.toStockHistoryDtoByKLine(history).stream()
                        .map(stockHistory -> stockHistory.setStockPoolId(stockPoolId).setStockCode(code))
                        .filter(stockHistoryDto -> Objects.nonNull(stockHistoryDto.getClose()))
                        .toList();

                stockHistoryService.save(stockHistoryList);
                log.info("更新股票记录：{} - {} - {}条", stockPoolDto.getPoolName(), code, stockHistoryList.size());
            });

            log.info("更新股票池成功：{}", stockPoolDto.getPoolName());
        }
    }

}
