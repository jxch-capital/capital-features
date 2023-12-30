package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.StockHistoryRepository;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.StockHistoryDto;
import org.jxch.capital.server.StockHistoryService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockHistoryServiceImpl implements StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;
    private final KLineMapper kLineMapper;

    @Override
    public Integer save(List<StockHistoryDto> stockHistoryDtoList) {
        return stockHistoryRepository.saveAllAndFlush(kLineMapper.toStockHistory(stockHistoryDtoList)).size();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void delByStockPoolId(@NonNull List<Long> stockPoolIds) {
        stockPoolIds.forEach(stockPoolId -> {
            stockHistoryRepository.deleteByStockPoolId(stockPoolId);
            log.info("删除股票池历史数据成功，ID：{}", stockPoolId);
        });
    }

    @Override
    @Cacheable(value = "findByStockPoolId", key = "#stockPoolId != null ? #stockPoolId : 0", unless = "#result == null")
    public List<StockHistoryDto> findByStockPoolId(Long stockPoolId) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findByStockPoolId(stockPoolId));
    }

}
