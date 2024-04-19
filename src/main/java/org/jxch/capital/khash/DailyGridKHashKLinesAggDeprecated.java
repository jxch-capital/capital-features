package org.jxch.capital.khash;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Getter
@Builder
public class DailyGridKHashKLinesAggDeprecated implements KHashAggDeprecated<List<KLine>> {
    @Builder.Default
    private DailyKSlicer dailyKSlicer = new DailyKSlicer();
    @Builder.Default
    private Integer dailyKNum = 48;
    @Builder.Default
    private Integer hisUpLength = 20;
    @Builder.Default
    private Integer hisDownLength = 20;
    @Builder.Default
    private Integer ranger = 4;
    @Builder.Default
    private Integer hashSkip = 10;
    @Builder.Default
    private final String leftFillChar = "0";

    @Override
    public Map<String, List<List<KLine>>> aggregate(List<KLine> kLines) {
        List<List<KLine>> sliceK = dailyKSlicer.slicer(kLines).stream().filter(kl -> kl.size() == dailyKNum).toList();
        Map<String, List<List<KLine>>> agg = new HashMap<>(sliceK.size());

        KLine referenceLine = null;
        int hisUp = 0, hisDown = 0;
        double fluctuationDownAvg = 0, fluctuationUpAvg = 0;
        double[] ups = new double[hisUpLength];
        double[] downs = new double[hisDownLength];

        for (List<KLine> dayK : sliceK) {
            if (hisUp > hisUpLength && hisDown > hisDownLength) {
                GridNumKHash gridNumKHash = GridNumKHash.builder()
                        .ranger(ranger)
                        .hashSkip(hashSkip)
                        .referenceLine(referenceLine)
                        .fluctuationDown(fluctuationDownAvg)
                        .fluctuationUp(fluctuationUpAvg)
                        .leftFillChar(leftFillChar)
                        .build();
                String key = gridNumKHash.hash(dayK);
                agg.putIfAbsent(key, new ArrayList<>());
                agg.get(key).add(dayK);
            }
            referenceLine = dayK.get(dayK.size() - 1);
            double high = dayK.stream().mapToDouble(KLine::getHigh).max().orElseThrow();
            double low = dayK.stream().mapToDouble(KLine::getLow).min().orElseThrow();
            if (referenceLine.getClose() < dayK.get(0).getOpen()) {
                ups[hisUp++ % (hisUpLength - 1)] = high - low;
                fluctuationUpAvg = Arrays.stream(ups).sum() / hisUp;
            } else {
                downs[hisDown++ % (hisDownLength - 1)] = high - low;
                fluctuationDownAvg = Arrays.stream(downs).sum() / hisDown;
            }
        }

        return agg;
    }

    public Integer hashLength() {
        return IntStream.range(0, dailyKNum).filter(i -> i % hashSkip == 0).toArray().length;
    }

}
