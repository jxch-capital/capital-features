package org.jxch.capital.khash;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.domain.dto.KLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Data
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DailyGridKHashKLinesAgg2HashSkips implements KHashAgg2List<KHash5Long5MCNDto> {
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
    private Boolean dailyKumValidate = true;
    @Builder.Default
    private Function<List<KLine>, List<KLine>> dayKLineProcessing = null;
    @Builder.Default
    private Function<KHash5Long5MCNDto, KHash5Long5MCNDto> setCodeFunc = null;
    @Builder.Default
    private String code = null;

    @Override
    public List<KHash5Long5MCNDto> agg(List<KLine> kLines) {
        KLine referenceLine = null;
        int hisUp = 0, hisDown = 0;
        double fluctuationDownAvg = 0, fluctuationUpAvg = 0;
        double[] ups = new double[hisUpLength];
        double[] downs = new double[hisDownLength];

        List<List<KLine>> sliceK = dailyKSlicer.slicer(kLines).stream().filter(kl -> !dailyKumValidate || kl.size() == dailyKNum)
                .map(dayKline -> Objects.nonNull(dayKLineProcessing) ? dayKLineProcessing.apply(dayKline) : dayKline).filter(Objects::nonNull).toList();

        if (sliceK.size() < hisUpLength + hisDownLength) {
            throw new IllegalArgumentException(String.format("不符合聚合操纵的前置条件，必须给定前%s天的前置数据", hisUpLength + hisDownLength));
        }

        List<KHash5Long5MCNDto> kHash5Long5MCNs = new ArrayList<>(sliceK.size());
        for (List<KLine> dayK : sliceK) {
            if (hisUp > hisUpLength && hisDown > hisDownLength) {
                GridNumKHash gridNumKHash = GridNumKHash.builder()
                        .ranger(ranger)
                        .referenceLine(referenceLine)
                        .fluctuationDown(fluctuationDownAvg)
                        .fluctuationUp(fluctuationUpAvg)
                        .build();
                List<String> fullHashCodeArr = gridNumKHash.fullHashCodeArr(dayK);
                KHash5Long5MCNDto dto = KHash5Long5MCNDto.builder()
                        .hash5l5s10(Long.parseLong(gridNumKHash.hash(10, fullHashCodeArr)))
                        .hash10l10s5(Long.parseLong(gridNumKHash.hash(5, fullHashCodeArr)))
                        .hash16l16s3(Long.parseLong(gridNumKHash.hash(3, fullHashCodeArr)))
                        .hash24sub1l12s2(Long.parseLong(gridNumKHash.hash(2, fullHashCodeArr).substring(0, 12)))
                        .hash24sub2l12s2(Long.parseLong(gridNumKHash.hash(2, fullHashCodeArr).substring(12, 24)))
                        .hash48sub1l12s1(Long.parseLong(gridNumKHash.hash(1, fullHashCodeArr).substring(0, 12)))
                        .hash48sub2l12s1(Long.parseLong(gridNumKHash.hash(1, fullHashCodeArr).substring(12, 24)))
                        .hash48sub3l12s1(Long.parseLong(gridNumKHash.hash(1, fullHashCodeArr).substring(24, 36)))
                        .hash48sub4l12s1(Long.parseLong(gridNumKHash.hash(1, fullHashCodeArr).substring(36, 48)))
                        .date(DateUtil.parse(DateUtil.newSimpleFormat("yyyyMMdd").format(dayK.get(0).getDate())))
                        .build();
                dto = Objects.isNull(setCodeFunc) ? (Objects.nonNull(code) ? dto.setCode(Integer.parseInt(code.split("\\.")[1])).setEx(code.split("\\.")[0]) : dto) : setCodeFunc.apply(dto);
                kHash5Long5MCNs.add(dto);
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

        return kHash5Long5MCNs;
    }

}
