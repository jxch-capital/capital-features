package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.yahoo.dto.DownloadStockCsvRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface KLineMapper {

    @Mappings({
            @Mapping(source = "date", target = "date"),
            @Mapping(source = "open", target = "open"),
            @Mapping(source = "high", target = "high"),
            @Mapping(source = "low", target = "low"),
            @Mapping(source = "close", target = "close"),
            @Mapping(source = "volume", target = "volume"),
    })
    KLine toKLine(DownloadStockCsvRes stock);

    default List<KLine> toKLine(@NonNull List<DownloadStockCsvRes> stocks) {
        return stocks.stream().map(this::toKLine).collect(Collectors.toList());
    }

}
