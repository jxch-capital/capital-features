package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.StockBaseDto;
import org.jxch.capital.domain.po.StockBase;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockBaseMapper {

    StockBaseDto toStockBaseDto(StockBase stockBase);

    List<StockBaseDto> toStockBaseDto(List<StockBase> stockBases);

    StockBase toStockBase(StockBaseDto stockBaseDto);

    List<StockBase> toStockBase(List<StockBaseDto> stockBaseDto);

}
