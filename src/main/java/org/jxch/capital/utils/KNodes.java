package org.jxch.capital.utils;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.domain.dto.KNode;

import java.util.ArrayList;
import java.util.List;

public class KNodes {

    public static List<KNode> subtractLast(@NonNull List<KNode> kNodes, int last) {
        return kNodes.stream().map(kNode -> kNode.subtractLast(last)).toList();
    }

    public static int[] futures(@NonNull List<KNode> kNodes, int futureNum) {
        return kNodes.stream().mapToInt(
                        kNode -> kNode.getLastKLine().getClose() - kNode.getBehind(futureNum).getClose() > 0 ? 1 : -1)
                .toArray();
    }

    @Nullable
    public static Double futureSignal(@NonNull KNode kNode, int futureNum, int size) {
        if (size - futureNum < kNode.getKLines().size()) {
            return kNode.getLastKLine().getClose() - kNode.get(size - futureNum).getClose();
        } else {
            return null;
        }
    }

    @NotNull
    public static double[][] normalizedIndicesArrH(@NonNull List<KNode> kNodes) {
        return kNodes.stream()
                .map(kNode -> KLineIndicesU.normalizedIndicesArrH(
                        kNode.getKLines().stream().map(kLine -> (KLineIndices) kLine).toList()))
                .toArray(double[][]::new);
    }

    @NotNull
    public static double[][] normalizedIndicesArrV(@NonNull List<KNode> kNodes) {
        return kNodes.stream()
                .map(kNode -> KLineIndicesU.normalizedIndicesArrV(
                        kNode.getKLines().stream().map(kLine -> (KLineIndices) kLine).toList()))
                .toArray(double[][]::new);
    }

    @NotNull
    public static double[][] normalizedKArrH(@NonNull List<KNode> kNodes) {
        return kNodes.stream()
                .map(kNode -> KLines.normalizedArrH(kNode.getKLines()))
                .toArray(double[][]::new);
    }

    @NotNull
    public static double[][] normalizedKArrV(@NonNull List<KNode> kNodes) {
        return kNodes.stream()
                .map(kNode -> KLines.normalizedArrV(kNode.getKLines()))
                .toArray(double[][]::new);
    }

    @NotNull
    public static List<KNode> sliceLastFuture(@NonNull List<KNode> nodes, int futureNum, int size) {
        KNode lastKNode = nodes.get(nodes.size() - 1);
        List<KNode> lastKNodes = lastKNode.sliceOut0(size);
        List<KNode> all = new ArrayList<>(KNodes.subtractLast(nodes, futureNum));
        all.addAll(lastKNodes);
        return all;
    }

}
