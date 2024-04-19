package org.jxch.capital.khash;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLine;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

@Data
@Slf4j
@Builder
public class GridNumKHash implements KHash {
    @Builder.Default
    private final Function<KLine, Double> refPriceGetter = KLine::getClose;
    @Builder.Default
    private final Function<KLine, Double> priceGetter = KLine::getClose;
    @Builder.Default
    private final Integer hashSkip = 1;
    @Builder.Default
    private final Integer ranger = 10;
    private final Double fluctuationUp;
    private final Double fluctuationDown;
    private final KLine referenceLine;
    @Builder.Default
    private final String leftFillChar = "0";

    @Override
    public String hash(@NotNull List<KLine> kLines) {
        List<String> hashcodeArr = fullHashCodeArr(kLines);
        return IntStream.range(0, hashcodeArr.size()).filter(i -> i % hashSkip == 0)
                .mapToObj(hashcodeArr::get)
                .reduce(String::concat).orElseThrow();
    }

    public List<String> fullHashCodeArr(@NotNull List<KLine> kLines) {
        Double open = priceGetter.apply(kLines.get(0));
        Double start = refPriceGetter.apply(referenceLine);
        double[] values = kLines.stream().mapToDouble(kLine -> (priceGetter.apply(kLine) / start) - 1).toArray();

        double max, min;
        if (open > start) {
            max = (open + fluctuationUp * 2 + open - start) / start - 1;
            min = (open - fluctuationDown) / start - 1;
        } else {
            max = (open + fluctuationUp) / start - 1;
            min = (open - fluctuationDown * 2 + start - open) / start - 1;
        }

        values = Arrays.stream(values).map(value -> (value - min) / (max - min))
                .map(value -> Math.max(0, Math.min(1, value))).toArray();

        return Arrays.stream(values).mapToObj(value -> {
            long gValue = Math.round(value * (ranger - 1));
            return String.format("%" + leftFillChar + String.valueOf(ranger).length() + "d", gValue + 1);
        }).toList();
    }

    public String hash(int skip, @NotNull List<String> hashcodeArr) {
        if (skip == 1) {
            return hashcodeArr.stream().reduce(String::concat).orElseThrow();
        }

        return IntStream.range(0, hashcodeArr.size()).filter(i -> i % skip == 0)
                .mapToObj(hashcodeArr::get)
                .reduce(String::concat).orElseThrow();
    }

    public static String hash(int skip, String hashcode) {
        if (skip == 1) {
            return hashcode;
        }
        List<String> hashcodeArr = Arrays.asList(hashcode.split(""));
        return IntStream.range(0, hashcodeArr.size()).filter(i -> i % skip == 0)
                .mapToObj(hashcodeArr::get)
                .reduce(String::concat).orElseThrow();
    }

}
