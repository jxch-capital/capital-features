package org.jxch.capital.utils;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLine;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class KLines {

    public static double[] normalizedArrH(@NonNull List<KLine> kLines) {
        List<Double> close = kLines.stream().map(KLine::getClose).toList();
        List<Double> open = kLines.stream().map(KLine::getOpen).toList();
        List<Double> high = kLines.stream().map(KLine::getHigh).toList();
        List<Double> low = kLines.stream().map(KLine::getLow).toList();

        List<Double> closeN = MathU.normalized(close);
        List<Double> openN = MathU.normalized(open);
        List<Double> highN = MathU.normalized(high);
        List<Double> lowN = MathU.normalized(low);

        return Stream.of(closeN, openN, highN, lowN)
                .flatMap(List::stream)
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    public static double[] normalizedArrV(@NonNull List<KLine> kLines) {
        return kLines.stream()
                .flatMap(kLine -> Arrays.stream(new Double[]{kLine.getClose(), kLine.getOpen(), kLine.getHigh(), kLine.getLow()}))
                .mapToDouble(value -> value)
                .toArray();
    }

    public static Double closePercent(@NonNull List<KLine> kLines, int num) {
        if (kLines.size() > num) {
            return (kLines.get(kLines.size() - 1).getClose() - kLines.get(kLines.size() - num).getClose())
                    / kLines.get(kLines.size() - num).getClose() * 100;
        } else {
            return Double.NaN;
        }
    }

    public static int  dayTradingKLinesIndexByTime(@NonNull List<KLine> kLines, @NonNull LocalTime time) {
        return IntStream.range(0, kLines.size())
                .filter(index -> DateUtil.toLocalDateTime(kLines.get(index).getDate()).toLocalTime().equals(time))
                .findAny().orElseThrow(() -> new IllegalArgumentException("无法找到对应的时间: " + time));
    }

    public static List<String> dayTradingKLabels(@NonNull List<KLine> kLines, @NonNull LocalTime openDate, @NonNull LocalTime closeDate) {
        int openIndex = dayTradingKLinesIndexByTime(kLines, openDate);
        int closeIndex = dayTradingKLinesIndexByTime(kLines, closeDate);

        return IntStream.range(0, kLines.size())
                .mapToObj(index -> {
                    if (index < openIndex) {
                        return "l" + (openIndex - index);
                    } else if (index <= closeIndex) {
                        return "k" + (index - openIndex);
                    } else {
                        return "r" + (index - closeIndex);
                    }
                }).toList();
    }

    public static <R> List<R> getKLineItem(@NotNull List<KLine> kLines, Function<KLine, R> itemFunc) {
        return kLines.stream().map(itemFunc).toList();
    }

}
