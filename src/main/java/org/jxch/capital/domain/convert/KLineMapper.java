package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.StockHistoryDto;
import org.jxch.capital.domain.po.StockHistory;
import org.jxch.capital.yahoo.dto.DownloadStockCsvRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KLineMapper {

    KLine toKLine(DownloadStockCsvRes stock);

    StockHistory toStockHistory(KLine kLine);

    StockHistory toStockHistory(StockHistoryDto stockHistoryDto);

    List<KLine> toKLine(List<DownloadStockCsvRes> stocks);

    List<StockHistoryDto> toStockHistoryDto(List<KLine> kLines);

    List<StockHistory> toStockHistory(List<StockHistoryDto> stockHistoryDtoList);

    default double[] toCloseArr(@NonNull List<KLine> kLines) {
        return kLines.stream().mapToDouble(KLine::getClose).toArray();
    }

    default double[] toOpenArr(@NonNull List<KLine> kLines) {
        return kLines.stream().mapToDouble(KLine::getOpen).toArray();
    }

    default double[] toHighArr(@NonNull List<KLine> kLines) {
        return kLines.stream().mapToDouble(KLine::getHigh).toArray();
    }

    default double[] toLowArr(@NonNull List<KLine> kLines) {
        return kLines.stream().mapToDouble(KLine::getLow).toArray();
    }

}
