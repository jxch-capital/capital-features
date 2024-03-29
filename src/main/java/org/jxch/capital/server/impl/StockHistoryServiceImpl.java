package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.StockHistoryRepository;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.StockHistoryDto;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.utils.AsyncU;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
//    @Cacheable(value = "findByStockPoolId",  unless = "#result == null")
    public List<StockHistoryDto> findByStockPoolId(Long stockPoolId) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findByStockPoolId(stockPoolId));
    }

    @Override
    @SneakyThrows
    public Map<String, List<StockHistoryDto>> findMapByStockPoolId(Long stockPoolId, Integer maxLength) {
        return AsyncU.newForkJoinPool().submit(() ->
                findByStockPoolId(stockPoolId).parallelStream()
                        .collect(Collectors.groupingBy(StockHistoryDto::getStockCode))
                        .entrySet().parallelStream()
                        .filter(entry -> entry.getValue().size() > maxLength)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream()
                                        .sorted(Comparator.comparing(StockHistoryDto::getDate))
                                        .collect(Collectors.toList())
                        ))
        ).get();
    }

    @Override
    @SneakyThrows
    public Map<String, List<StockHistoryDto>> findMapByStockPoolId(Long stockPoolId, List<String> codes, Integer maxLength) {
        return AsyncU.newForkJoinPool().submit(() ->
                findByStockPoolIdAndStockCode(stockPoolId, codes).parallelStream()
                        .collect(Collectors.groupingBy(StockHistoryDto::getStockCode))
                        .entrySet().parallelStream()
                        .filter(entry -> entry.getValue().size() > maxLength)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().stream()
                                        .sorted(Comparator.comparing(StockHistoryDto::getDate))
                                        .collect(Collectors.toList())
                        ))
        ).get();
    }

    @Override
    public List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findByStockPoolIdAndStockCode(stockPoolId, stockCode));
    }

    @Override
    public List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, List<String> stockCodes) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findByStockPoolIdAndStockCode(stockPoolId, stockCodes));
    }

    @Override
    public List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode, Date startDate, Date endDate) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findByStockPoolIdAndStockCode(stockPoolId, stockCode, startDate, endDate));
    }

    @Override
    public List<StockHistoryDto> findByStockCodeAndDate(String stockCode, Date start, Date end) {
        return kLineMapper.toStockHistoryDto(stockHistoryRepository.findAllByStockCodeAndDateBetween(stockCode, start, end));
    }

    @Override
    public void delByStockPoolIdAndCodes(Long stockPoolId, List<String> codes) {
        stockHistoryRepository.deleteAllByStockPoolIdAndStockCodes(stockPoolId, codes);
    }

    @Override
    public List<List<List<Double>>> stockPoolPrices(Long stockPoolId, List<String> codes, int maxLength) {
        return findMapByStockPoolId(stockPoolId, codes, maxLength).values().parallelStream().map(stockHistoryDtoList ->
                stockHistoryDtoList.stream().map(stockHistoryDto -> Arrays.asList(stockHistoryDto.getOpen(), stockHistoryDto.getHigh(), stockHistoryDto.getLow(), stockHistoryDto.getClose())).toList()).toList();
    }


}
