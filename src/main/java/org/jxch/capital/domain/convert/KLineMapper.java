package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.yahoo.dto.DownloadStockCsvRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineMapper {

    KLine toKLine(DownloadStockCsvRes stock);

    List<KLine> toKLine(@NonNull List<DownloadStockCsvRes> stocks);

}
