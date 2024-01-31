package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockHistoryDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StockHistoryService {

    Integer save(List<StockHistoryDto> stockHistoryDtoList);

    void delByStockPoolId(List<Long> stockPoolIds);

    List<StockHistoryDto> findByStockPoolId(Long stockPoolId);

    Map<String, List<StockHistoryDto>> findMapByStockPoolId(Long stockPoolId, Integer maxLength);

    Map<String, List<StockHistoryDto>> findMapByStockPoolId(Long stockPoolId, List<String> codes, Integer maxLength);

    List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode);

    List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, List<String> stockCodes);

    List<StockHistoryDto> findByStockPoolIdAndStockCode(Long stockPoolId, String stockCode, Date startDate, Date endDate);

    List<StockHistoryDto> findByStockCodeAndDate(String stockCode, Date start, Date end);

    void delByStockPoolIdAndCodes(Long stockPoolId, List<String> codes);
}
