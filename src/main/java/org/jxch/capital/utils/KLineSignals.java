package org.jxch.capital.utils;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNode;

import java.util.ArrayList;
import java.util.List;

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


}
