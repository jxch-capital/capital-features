package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.dao.StockPoolRepository;
import org.jxch.capital.domain.convert.StockPoolMapper;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.StockPoolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPoolServiceImpl implements StockPoolService {
    private final StockPoolRepository stockPoolRepository;
    private final StockPoolMapper stockPoolMapper;

    @Override
    public List<StockPoolDto> findAll() {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findAll());
    }

    @Override
    public Integer save(List<StockPoolDto> stockPools) {
        return stockPoolRepository.saveAllAndFlush(stockPoolMapper.toStockPool(stockPools)).size();
    }

}
