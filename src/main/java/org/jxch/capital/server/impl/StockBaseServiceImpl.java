package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.StockBaseRepository;
import org.jxch.capital.domain.convert.StockBaseMapper;
import org.jxch.capital.domain.dto.StockBaseDto;
import org.jxch.capital.server.StockBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockBaseServiceImpl implements StockBaseService {
    private final StockBaseRepository stockBaseRepository;
    private final StockBaseMapper stockBaseMapper;

    @Override
    public List<StockBaseDto> findAll() {
        return stockBaseMapper.toStockBaseDto(stockBaseRepository.findAll());
    }

    @Override
    public List<StockBaseDto> findByCode(List<String> codes) {
        return stockBaseMapper.toStockBaseDto(stockBaseRepository.findByCode(codes));
    }

    @Override
    public Integer save(List<StockBaseDto> stockBaseDtoList) {
        return stockBaseRepository.saveAllAndFlush(stockBaseMapper.toStockBase(stockBaseDtoList)).size();
    }

    @Override
    public void del(List<Long> ids) {
        stockBaseRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public List<StockBaseDto> findById(List<Long> ids) {
        return stockBaseMapper.toStockBaseDto(stockBaseRepository.findAllById(ids));
    }

    @Override
    public StockBaseDto findById(Long id) {
        return stockBaseMapper.toStockBaseDto(stockBaseRepository.findById(id).orElseThrow());
    }

}
