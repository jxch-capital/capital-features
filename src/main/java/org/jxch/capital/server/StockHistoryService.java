package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockHistoryDto;

import java.util.List;

public interface StockHistoryService {

    Integer save(List<StockHistoryDto> stockHistoryDtoList);

    void delByStockPoolId(List<Long> stockPoolIds);

}
