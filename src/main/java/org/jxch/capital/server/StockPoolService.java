package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockPoolDto;

import java.util.List;

public interface StockPoolService {
    List<StockPoolDto> findAll();

    StockPoolDto findById(Long id);

    Integer save(List<StockPoolDto> stockPoolDto);

    void del(List<Long> id);
}
