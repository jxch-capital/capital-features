package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockHistoryDto;

import java.util.Date;
import java.util.List;

public interface StockHistoryService {

    Integer save(List<StockHistoryDto> stockHistoryDtoList);

    void delByStockPoolId(List<Long> stockPoolIds);

    List<StockHistoryDto> findByStockPoolId(Long stockPoolId);

    List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode);

    List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode, Date startDate, Date endDate);

    List<StockHistoryDto> findByStockCodeAndDate(String stockCode, Date start, Date end);
}
