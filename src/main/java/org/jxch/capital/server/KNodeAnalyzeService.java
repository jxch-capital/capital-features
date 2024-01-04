package org.jxch.capital.server;

import cn.hutool.core.date.DateField;
import lombok.NonNull;
import org.jxch.capital.domain.dto.*;

import java.util.List;

public interface KNodeAnalyzeService {

    List<KLine> search(KLineAnalyzedParam param);

    KLineAnalyzes analyze(KLineAnalyzedParam param);

    KLineAnalyzeStatistics statistics(List<KLineAnalyzes> analyzes);

    default KLineAnalyzeStatistics statisticsKNN(@NonNull List<KNeighbor> neighbors, @NonNull KNNParam knnParam) {
        return statistics(neighbors.stream().map(kNeighbor -> analyze(buildAnalyzedParam(knnParam, kNeighbor))).toList());
    }

    default KLineAnalyzeStatistics statisticsKNNHasFuture(@NonNull List<KNeighbor> neighbors, int futureNum) {
        return statistics(neighbors.stream().map(kNeighbor -> KLineAnalyzes.builder()
                .code(kNeighbor.getKNode().getCode())
                .startDate(kNeighbor.getKNode().getFristKLine().getDate())
                .endDate(kNeighbor.getKNode().subtractLast(futureNum).getLastKLine().getDate())
                .futureNum(futureNum)
                .kLines(kNeighbor.getKNode().getKLines())
                .build().analyze()).toList());
    }

    default KLineAnalyzedParam buildAnalyzedParam(@NonNull KNNParam knnParam, @NonNull KNeighbor kNeighbor) {
        return KLineAnalyzedParam.builder()
                .stockPoolId(knnParam.getKNodeParam().getStockPoolId())
                .stockCode(kNeighbor.getKNode().getCode())
                .startDate(kNeighbor.getKNode().getKLines().get(0).getDate())
                .endDate(kNeighbor.getKNode().getKLines().get(kNeighbor.getKNode().getKLines().size() - 1).getDate())
                .dateField(DateField.DAY_OF_YEAR)
                .extend(Math.max(knnParam.getKNodeParam().getSize() + knnParam.getKNodeParam().getFutureNum(),
                        Math.max(2 * knnParam.getKNodeParam().getFutureNum(), 20)))
                .futureNum(knnParam.getKNodeParam().getFutureNum())
                .build();
    }

}
