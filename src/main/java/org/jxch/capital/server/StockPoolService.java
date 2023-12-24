package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockPoolDto;

import java.util.List;

public interface StockPoolService {
    List<StockPoolDto> findAll();

    Integer save(List<StockPoolDto> stockPoolDto);
}
