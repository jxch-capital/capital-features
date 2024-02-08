package org.jxch.capital.utils;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.domain.vo.Model3SignalParam;
import org.jxch.capital.learning.model.dto.Model3PredictRes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class KLineSignals {

    public static List<KLineSignal> setTrueSignal(@NonNull List<KNode> nodes, int futureNum, int size) {
        return nodes.stream()
                .map(kNode -> KLineSignal.builder()
                        .kLine(kNode.get(size - futureNum))
                        .tureSignal(kNode.getLastKLine().getClose() - kNode.get(size - futureNum).getClose())
                        .build()).toList();
    }

    @NotNull
    public static List<KLineSignal> setTrueSignalHasLastNull(List<KNode> nodes, int futureNum, int size) {
        List<KLineSignal> kLineSignals = new ArrayList<>(setTrueSignal(nodes, futureNum, size));

        KNode lastKNode = nodes.get(nodes.size() - 1);
        List<KNode> lastKNodes = lastKNode.sliceOut0(size);
        List<KLineSignal> lastNullSignals = lastKNodes.stream()
                .map(kNode -> KLineSignal.builder().kLine(kNode.getLastKLine()).build())
                .toList();

        kLineSignals.addAll(lastNullSignals);
        return kLineSignals;
    }

    public static List<KLineSignal> setActionSignal(@NonNull List<KLineSignal> signals) {
        return signals.stream().map(KLineSignal::actionSignal).toList();
    }

    public static List<KLine> toKLines(@NonNull List<KLineSignal> kLineSignals) {
        return kLineSignals.stream().map(KLineSignal::getKLine).toList();
    }

    public static List<KLine> toKLinesByStacks(@NonNull List<KLineSignalStackDto> kLineSignalStacks) {
        return kLineSignalStacks.stream().map(stackDto -> stackDto.getKLineSignal().getKLine()).toList();
    }

    public static List<KLineSignal> toKLineSignalByStacks(@NonNull List<KLineSignalStackDto> kLineSignalStacks) {
        return kLineSignalStacks.stream().map(KLineSignalStackDto::getKLineSignal).toList();
    }

    public static List<EChartsMainIndexDto<Double>> toEChartDtoSignals(@NonNull List<KLineSignal> kLineSignals) {
        return kLineSignals.stream().map(kLineSignal -> new EChartsMainIndexDto<>(kLineSignal.getKLine().getDate(), kLineSignal.getSignal())).toList();
    }

    public static List<EChartsMainIndexDto<Integer>> toEChartDtoActionSignals(@NonNull List<KLineSignal> kLineSignals) {
        return kLineSignals.stream().map(kLineSignal -> new EChartsMainIndexDto<>(kLineSignal.getKLine().getDate(), kLineSignal.getActionSignal())).toList();
    }

    @NotNull
    @Contract("_, _ -> new")
    public static KLineSignalStatistics toKLineSignalStatistics(@NonNull List<KLineSignal> kLineSignals, int limitAbs) {
        return new KLineSignalStatistics(kLineSignals, limitAbs);
    }

    public static KLineSignalStatistics toKLineSignalStatistics(@NonNull List<KLineSignal> kLineSignals, double limitAbs) {
        return new KLineSignalStatistics(kLineSignals, limitAbs);
    }

    @NotNull
    public static KLineSignalStatistics toKLineSignalStatistics(@NonNull List<KLineSignal> kLineSignals, int limitAbs, int futureSize) {
        return new KLineSignalStatistics(kLineSignals, limitAbs).resetTureSignalByFutureSize(futureSize);
    }

    public static List<KLineSignal> toKLineSignal(@NotNull Model3PredictRes model3PredictRes, @NotNull Model3SignalParam param) {
        List<KLine> kLine = model3PredictRes.getKLine();
        List<Double> signals = model3PredictRes.getSignals();
        List<KLineSignal> kLineSignals = IntStream.range(0, kLine.size()).mapToObj(index -> KLineSignal.builder()
                        .code(model3PredictRes.getPredictionDataParam().getCode())
                        .kLine(kLine.get(index))
                        .signal(signals.get(index))
                        .tureSignal(param.getFutureNum() + index < kLine.size() ? kLine.get(param.getFutureNum() + index).getClose() - kLine.get(index).getClose() : null)
                        .build())
                .peek(kLineSignal -> kLineSignal.actionSignal(param.getLimitAbs()))
                .toList();

        if (param.getMaskContinuousSignals()) {
            for (int i = 0; i < kLineSignals.size() - 1; i++) {
                if (kLineSignals.get(i).isActionSignal() && kLineSignals.get(i + 1).isActionSignal()) {
                    kLineSignals.get(i).resetActionSignal();
                }
            }
        }

        return kLineSignals;
    }

}
