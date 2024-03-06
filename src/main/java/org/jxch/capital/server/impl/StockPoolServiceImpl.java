package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.StockPoolRepository;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.convert.StockPoolMapper;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IndexService;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.stock.EngineEnum;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.utils.CollU;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolServiceImpl implements StockPoolService {
    private final StockPoolRepository stockPoolRepository;
    private final StockPoolMapper stockPoolMapper;
    private final StockService stockService;
    private final StockHistoryService stockHistoryService;
    private final KLineMapper kLineMapper;
    private final IndicesCombinationService indicesCombinationService;
    private final IndexService indexService;

    @Override
    public List<StockPoolDto> findAll() {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findAll());
    }

    @Override
    public List<StockPoolDto> findAllInSorted() {
        return findAll().stream().sorted(Comparator.comparing(StockPoolDto::getPoolName)).toList();
    }

    @Override
    public StockPoolDto findById(Long id) {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findById(id).orElseThrow());
    }

    @Override
    public List<StockPoolDto> findById(List<Long> ids) {
        return stockPoolMapper.toStockPoolDto(stockPoolRepository.findAllById(ids));
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

    private void updateCodes(@NonNull List<String> codes, StockPoolDto stockPoolDto) {
        codes.forEach(code -> {
            List<KLine> history = stockService.history(HistoryParam.builder()
                    .code(code)
                    .start(stockPoolDto.getStartDate())
                    .end(stockPoolDto.getEndDate())
                    .interval(stockPoolDto.getInterval())
                    .engine(EngineEnum.pares(stockPoolDto.getEngine()))
                    .build());
            List<StockHistoryDto> stockHistoryList = kLineMapper.toStockHistoryDtoByKLine(history).parallelStream()
                    .map(stockHistory -> stockHistory.setStockPoolId(stockPoolDto.getId()).setStockCode(code))
                    .filter(stockHistoryDto -> Objects.nonNull(stockHistoryDto.getClose()) && stockHistoryDto.getDate().getTime() <= stockPoolDto.getEndDate().getTime())
                    .toList();

            stockHistoryService.save(stockHistoryList);
            log.info("更新股票记录：{} - {} - {}条", stockPoolDto.getPoolName(), code, stockHistoryList.size());
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void update(@NonNull List<Long> stockPoolIds) {
        for (Long stockPoolId : stockPoolIds) {
            stockHistoryService.delByStockPoolId(Collections.singletonList(stockPoolId));
            StockPoolDto stockPoolDto = findById(stockPoolId);
            this.updateCodes(stockPoolDto.getPoolStockList(), stockPoolDto);
            log.info("更新股票池成功：{}", stockPoolDto.getPoolName());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateToNow(@NonNull List<Long> stockPoolIds) {
        for (Long stockPoolId : stockPoolIds) {
            List<StockHistoryDto> historyDtoList = stockHistoryService.findByStockPoolId(stockPoolId);
            List<String> dbCodes = historyDtoList.stream().map(StockHistoryDto::getStockCode).distinct().toList();

            // 更新股票池时间
            StockPoolDto stockPoolDto = findById(stockPoolId);
            stockPoolDto.setEndDate(Calendar.getInstance().getTime());
            save(Collections.singletonList(stockPoolDto));
            List<String> configCodes = stockPoolDto.getPoolStockList();

            // 新增的股票
            List<String> newCodes = new ArrayList<>(configCodes);
            newCodes.removeAll(dbCodes);
            this.updateCodes(newCodes, stockPoolDto);

            // 删除的股票
            List<String> delCodes = new ArrayList<>(dbCodes);
            delCodes.removeAll(configCodes);
            stockHistoryService.delByStockPoolIdAndCodes(stockPoolId, delCodes);

            // 更新至今日的股票
            Date lastDate = historyDtoList.get(historyDtoList.size() - 1).getDate();
            List<String> updateCodes = new ArrayList<>(configCodes);
            updateCodes.removeAll(newCodes);

            updateCodes.forEach(code -> {
                List<KLine> history = stockService.history(HistoryParam.builder()
                        .code(code)
                        .start(lastDate)
                        .end(stockPoolDto.getEndDate())
                        .interval(stockPoolDto.getInterval())
                        .engine(EngineEnum.pares(stockPoolDto.getEngine()))
                        .build());

                List<StockHistoryDto> stockHistoryList = kLineMapper.toStockHistoryDtoByKLine(history).stream()
                        .map(stockHistory -> stockHistory.setStockPoolId(stockPoolId).setStockCode(code))
                        .filter(stockHistoryDto -> Objects.nonNull(stockHistoryDto.getClose()) && stockHistoryDto.getDate().getTime() > lastDate.getTime())
                        .toList();

                stockHistoryService.save(stockHistoryList);
                log.info("更新股票记录：{} - {} - {}条", stockPoolDto.getPoolName(), code, stockHistoryList.size());
            });

            log.info("更新股票池成功：{}", stockPoolDto.getPoolName());
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateNewCode(Long stockPoolId) {
        List<StockHistoryDto> historyDtoList = stockHistoryService.findByStockPoolId(stockPoolId);
        List<String> dbCodes = historyDtoList.stream().map(StockHistoryDto::getStockCode).distinct().toList();

        StockPoolDto stockPoolDto = findById(stockPoolId);
        List<String> configCodes = stockPoolDto.getPoolStockList();
        List<String> newCodes = new ArrayList<>(configCodes);
        newCodes.removeAll(dbCodes);

        log.info("新增股票：{}", String.join(",", newCodes));
        this.updateCodes(newCodes, stockPoolDto);

        log.info("更新股票池成功：{}", stockPoolDto.getPoolName());
    }

    @Override
    public Long getTopPoolId(Long id) {
        return findById(id).getTopPoolId();
    }

    @Override
    public List<List<List<Double>>> stockPoolPrices(Long stockPoolId, int maxLength) {
        StockPoolDto stockPoolDto = findById(stockPoolId);
        return stockHistoryService.findMapByStockPoolId(getTopPoolId(stockPoolId), stockPoolDto.getPoolStockList(), maxLength).values().parallelStream()
                .map(stockHistoryDtoList -> stockHistoryDtoList.stream()
                        .filter(stockHistoryDto -> stockHistoryDto.getDate().getTime() >= stockPoolDto.getStartDate().getTime() && stockHistoryDto.getDate().getTime() <= stockPoolDto.getEndDate().getTime())
                        .map(stockHistoryDto -> Arrays.asList(stockHistoryDto.getOpen(), stockHistoryDto.getHigh(), stockHistoryDto.getLow(), stockHistoryDto.getClose())).toList()).toList();
    }

    @Override
    public List<List<List<Double>>> stockPoolPricesByIc(Long stockPoolId, Long icId, int maxLength) {
        List<IndicatorWrapper> indicatorWrappers = indicesCombinationService.getIndicatorWrapper(icId);
        List<String> indicators = indicatorWrappers.stream().map(IndicatorWrapper::getName).toList();
        StockPoolDto stockPoolDto = findById(stockPoolId);
        return stockHistoryService.findMapByStockPoolId(getTopPoolId(stockPoolId), stockPoolDto.getPoolStockList(), maxLength).values().parallelStream()
                .map(kLineMapper::toKLineByStockHistoryDto)
                .map(kLines -> indexService.indexAndNormalized(kLines, Duration.ofDays(1), indicatorWrappers))
                .map(kLineIndices -> kLineIndices.subList(maxLength, kLineIndices.size()))
                .map(kLineIndices -> kLineIndices.stream().filter(kLine -> kLine.getDate().getTime() >= stockPoolDto.getStartDate().getTime() && kLine.getDate().getTime() <= stockPoolDto.getEndDate().getTime()).toList())
                .map(kLineIndices -> kLineIndices.stream().map(kline -> CollU.addAllToArrayList(Arrays.asList(kline.getOpen(), kline.getHigh(), kline.getLow(), kline.getClose()), kline.get(indicators))).toList()).toList();
    }

}
