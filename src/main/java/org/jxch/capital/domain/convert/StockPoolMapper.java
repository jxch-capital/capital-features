package org.jxch.capital.domain.convert;


import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.domain.po.StockPool;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockPoolMapper {

    StockPoolDto toStockPoolDto(StockPool stockPool);

    StockPool toStockPool(StockPoolDto stockPool);

    //    default
    List<StockPoolDto> toStockPoolDto(List<StockPool> stockPools);

    List<StockPool> toStockPool(List<StockPoolDto> stockPools);

}
