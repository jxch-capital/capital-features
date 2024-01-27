package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockPoolDto;

import java.util.List;

public interface StockPoolService {
    List<StockPoolDto> findAll();

    StockPoolDto findById(Long id);

    List<StockPoolDto> findById(List<Long> ids);

    Integer save(List<StockPoolDto> stockPoolDto);

    void del(List<Long> id);

    void update(List<Long> ids);

    void updateToNow(List<Long> ids);

    void updateNewCode(Long id);
}
