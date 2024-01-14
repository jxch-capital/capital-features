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
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.stock.EngineEnum;
import org.jxch.capital.stock.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void updateCodes(@NonNull List<String> codes, StockPoolDto stockPoolDto) {
        codes.forEach(code -> {
            List<KLine> history = stockService.history(HistoryParam.builder()
                    .code(code)
                    .start(stockPoolDto.getStartDate())
                    .end(stockPoolDto.getEndDate())
                    .interval(stockPoolDto.getInterval())
                    .engine(EngineEnum.pares(stockPoolDto.getEngine()))
                    .build());
            List<StockHistoryDto> stockHistoryList = kLineMapper.toStockHistoryDtoByKLine(history).stream()
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

}
