package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.server.IndexService;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.utils.AsyncU;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class KNodeServiceImpl implements KNodeService {
    private final StockHistoryService stockHistoryService;
    private final StockService stockService;
    private final KLineMapper kLineMapper;
    private final IndexService indexService;
    private final IndicesCombinationService indicesCombinationService;
    @Setter
    private static int offsetMultiples = 5;
    @Setter
    private static int defaultOffset = 30;

    private void setIndicators(@NonNull KNodeParam kNodeParam) {
        if (kNodeParam.hasIndicesComId()) {
            kNodeParam.addIndicators(indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId()));
            kNodeParam.setMaxLength(indicesCombinationService.findById(kNodeParam.getIndicesComId()).getMaxLength());
        }
    }

    @Override
    public List<KNode> kNodes(@NonNull KNodeParam kNodeParam, Date start, Date end) {
        List<KLine> kLines = stockService.history(HistoryParam.builder()
                .code(kNodeParam.getCode())
                .start(start)
                .end(end)
                .interval(kNodeParam.getIntervalEnum().getInterval())
                .build());

        return kNodes(kNodeParam, kLines);
    }

    @Override
    public List<KNode> kNodes(KNodeParam kNodeParam, List<KLine> kLines) {
        setIndicators(kNodeParam);
        if (kNodeParam.hasIndicatorWrappers()) {
            if (kNodeParam.getNormalized()) {
                kLines = indexService.indexAndNormalized(kLines, Duration.ofDays(1), kNodeParam.getIndicatorWrappers()).stream()
                        .map(kLineIns -> (KLine) kLineIns)
                        .sorted(Comparator.comparing(KLine::getDate).reversed())
                        .limit(kLines.size() - kNodeParam.getMaxLength())
                        .sorted(Comparator.comparing(KLine::getDate))
                        .toList();
            } else {
                kLines = indexService.index(kLines, Duration.ofDays(1), kNodeParam.getIndicatorWrappers()).stream()
                        .map(kLineIns -> (KLine) kLineIns)
                        .sorted(Comparator.comparing(KLine::getDate).reversed())
                        .limit(kLines.size() - kNodeParam.getMaxLength())
                        .sorted(Comparator.comparing(KLine::getDate))
                        .toList();
            }
        }

        List<KLine> finalKLines = kLines;
        return IntStream.range(0, kLines.size() - kNodeParam.getSize() + 1)
                .mapToObj(startIndex -> finalKLines.subList(startIndex, startIndex + kNodeParam.getSize()))
                .map(kList -> KNode.builder().code(kNodeParam.getCode()).kLines(kList).build())
                .toList();
    }

    @Override
    public KNode kNode(@NonNull KNodeParam kNodeParam) {
        setIndicators(kNodeParam);
        List<KLine> history = stockService.history(HistoryParam.builder()
                .code(kNodeParam.getCode())
                .start(DateUtil.offset(kNodeParam.getEnd(), DateField.DAY_OF_YEAR,
                        -kNodeParam.getSize() * offsetMultiples - defaultOffset - kNodeParam.getMaxLength() * offsetMultiples))
                .end(kNodeParam.getEnd())
                .interval(kNodeParam.getIntervalEnum().getInterval())
                .build());


        if (kNodeParam.hasIndicatorWrappers()) {
            List<KLine> kLineIndices;
            if (kNodeParam.getNormalized()) {
                kLineIndices = indexService.indexAndNormalized(history, Duration.ofDays(1), kNodeParam.getIndicatorWrappers())
                        .stream()
                        .sorted(Comparator.comparing(KLine::getDate).reversed())
                        .limit(kNodeParam.getSize())
                        .sorted(Comparator.comparing(KLine::getDate))
                        .map(kLineIns -> (KLine) kLineIns)
                        .toList();
            } else {
                kLineIndices = indexService.index(history, Duration.ofDays(1), kNodeParam.getIndicatorWrappers())
                        .stream()
                        .sorted(Comparator.comparing(KLine::getDate).reversed())
                        .limit(kNodeParam.getSize())
                        .sorted(Comparator.comparing(KLine::getDate))
                        .map(kLineIns -> (KLine) kLineIns)
                        .toList();
            }

            return KNode.builder()
                    .code(kNodeParam.getCode())
                    .kLines(kLineIndices)
                    .build();
        } else {
            history = history.stream()
                    .sorted(Comparator.comparing(KLine::getDate).reversed())
                    .limit(kNodeParam.getSize())
                    .sorted(Comparator.comparing(KLine::getDate))
                    .toList();
            return KNode.builder()
                    .code(kNodeParam.getCode())
                    .kLines(history)
                    .build();
        }
    }

    @Override
    public KNode current(@NonNull KNodeParam kNodeParam) {
        return kNode(kNodeParam.setEnd(Calendar.getInstance().getTime()));
    }


    @SneakyThrows
    private List<KNode> getComparisonList(@NonNull KNodeParam kNodeParam) {
        if (kNodeParam.hasIndicesComId()) {
            kNodeParam.addIndicators(indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId()));
        }

        if (kNodeParam.hasIndicatorWrappers()) {
            return AsyncU.newForkJoinPool().submit(() ->
                    stockHistoryService.findMapByStockPoolId(kNodeParam.getStockPoolId(), kNodeParam.getMaxLength())
                            .entrySet().stream().parallel().flatMap(entry -> {
                                        List<KLine> kLines = kLineMapper.toKLineByStockHistoryDto(entry.getValue());

                                        List<KLine> kLineIndices;
                                        if (kNodeParam.getNormalized()) {
                                            kLineIndices = indexService.indexAndNormalized(kLines, Duration.ofDays(1), kNodeParam.getIndicatorWrappers())
                                                    .stream().map(kLineInd -> (KLine) kLineInd).toList().subList(kNodeParam.getMaxLength(), kLines.size());
                                        } else {
                                            kLineIndices = indexService.index(kLines, Duration.ofDays(1), kNodeParam.getIndicatorWrappers())
                                                    .stream().map(kLineInd -> (KLine) kLineInd).toList().subList(kNodeParam.getMaxLength(), kLines.size());
                                        }

                                        log.info("处理完成：{}", entry.getKey());
                                        return IntStream.range(0, kLineIndices.size() - kNodeParam.getSize() + 1)
                                                .mapToObj(start -> kLineIndices.subList(start, start + kNodeParam.getSize()))
                                                .map(kIndList -> KNode.builder().code(entry.getKey()).kLines(kIndList).build());
                                    }
                            ).toList()).get();
        } else {
            return AsyncU.newForkJoinPool().submit(() ->
                    stockHistoryService.findMapByStockPoolId(kNodeParam.getStockPoolId(), kNodeParam.getSize())
                            .entrySet().stream().parallel().flatMap(entry ->
                                    IntStream.range(0, entry.getValue().size() - kNodeParam.getSize() + 1)
                                            .mapToObj(start -> entry.getValue().subList(start, start + kNodeParam.getSize()))
                                            .map(kLines -> KNode.builder().code(entry.getKey()).kLines(kLineMapper.toKLineByStockHistoryDto(kLines)).build())
                            ).toList()).get();
        }
    }

    @Override
    public List<KNode> comparison(@NonNull KNodeParam kNodeParam) {
        return getComparisonList(kNodeParam);
    }

}
