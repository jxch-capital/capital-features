package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.yahoo.dto.DownloadStockCsvParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface HistoryParamMapper {

    @Mappings({
            @Mapping(source = "code", target = "code"),
            @Mapping(source = "start", target = "start"),
            @Mapping(source = "end", target = "end"),
            @Mapping(source = "interval", target = "interval"),
    })
    DownloadStockCsvParam toDownloadStockCsvParam(HistoryParam param);

}
