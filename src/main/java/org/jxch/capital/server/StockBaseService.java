package org.jxch.capital.server;

import org.jxch.capital.domain.dto.StockBaseDto;

import java.util.List;

public interface StockBaseService {

    List<StockBaseDto> findAll();

    List<StockBaseDto> findByCode(List<String> codes);

    StockBaseDto findByCode(String code);

    Integer save(List<StockBaseDto> stockBaseDtoList);

    void del(List<Long> ids);

    List<StockBaseDto> findById(List<Long> ids);

    StockBaseDto findById(Long id);

}
