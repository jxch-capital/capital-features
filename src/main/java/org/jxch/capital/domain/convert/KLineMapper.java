package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.domain.po.StockHistory;
import org.jxch.capital.http.yahoo.dto.ChartRes;
import org.jxch.capital.http.yahoo.dto.DownloadStockCsvRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseBarSeries;
import org.ta4j.core.num.DecimalNum;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Mapper(componentModel = "spring")
public interface KLineMapper {
    KLineMapper K_LINE_MAPPER = Mappers.getMapper(KLineMapper.class);

    KLine toKLine(DownloadStockCsvRes stock);

    KLine toKLine(StockHistoryDto stock);

    KLine toKLine(KLineIndices kLineIndices);

    List<KLineIndices> toKLineIndices(List<KLine> kLines);

    StockHistory toStockHistory(StockHistoryDto stockHistoryDto);

    List<KLine> toKLine(List<DownloadStockCsvRes> stocks);

    List<KLine> toKLineByStockHistoryDto(List<StockHistoryDto> stocks);

    List<StockHistoryDto> toStockHistoryDtoByKLine(List<KLine> kLines);

    List<StockHistoryDto> toStockHistoryDto(List<StockHistory> kLines);

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

    default BaseBar toBaseBar(@NonNull KLine kLine, Duration duration) {
        return BaseBar.builder()
                .timePeriod(duration)
                .endTime(ZonedDateTime.ofInstant(kLine.getDate().toInstant(), ZoneId.systemDefault()))
                .closePrice(DecimalNum.valueOf(kLine.getClose()))
                .openPrice(DecimalNum.valueOf(kLine.getOpen()))
                .highPrice(DecimalNum.valueOf(kLine.getHigh()))
                .lowPrice(DecimalNum.valueOf(kLine.getLow()))
                .volume(DecimalNum.valueOf(kLine.getVolume()))
                .build();
    }

    default BarSeries toBarSeries(@NonNull List<KLine> kLines, Duration duration) {
        BarSeries series = new BaseBarSeries();
        kLines.forEach(kLine -> series.addBar(toBaseBar(kLine, duration)));
        return series;
    }

    KLineFeatures toKLineFeatures(KLine kLine);

    default KLineFeatures toKLineFeatures(KLineIndices kLineIndices) {
        KLineFeatures features = toKLineFeatures(toKLine(kLineIndices));
        kLineIndices.getIndices().values().forEach(features::addFeature);
        return features;
    }

    default List<KLineFeatures> toKLineFeatures(@NonNull List<KLineIndices> kLineIndices) {
        return kLineIndices.stream().map(this::toKLineFeatures).toList();
    }

    default KLineSignal toKLineSignal(KLine kLine) {
        return KLineSignal.builder().kLine(kLine).build();
    }

    default List<KLineSignal> toKLineSignal(@NonNull List<KLine> kLines, int futureSize, String code) {
        List<KLineSignal> kLineSignals = new ArrayList<>();
        for (int i = 0; i < kLines.size(); i++) {
            KLineSignal kLineSignal = toKLineSignal(kLines.get(i));
            kLineSignal.setCode(code);
            if (i + futureSize < kLines.size()) {
                kLineSignal.setTureSignal(kLines.get(i + futureSize).getClose() - kLines.get(i).getClose());
            }
            kLineSignals.add(kLineSignal);
        }
        return kLineSignals;
    }

    default List<KLine> toKLineByYahooChartRes(@NonNull ChartRes chartRes) {
        return IntStream.range(0, chartRes.getLengthThrow()).parallel().mapToObj(index -> new KLine()
                .setDate(chartRes.getDate(index))
                .setClose(chartRes.getClose(index))
                .setOpen(chartRes.getOpen(index))
                .setLow(chartRes.getLow(index))
                .setHigh(chartRes.getHigh(index))
                .setVolume(chartRes.getLongVolume(index))
        ).filter(kLine -> Objects.nonNull(kLine.getClose())).toList();
    }

}
