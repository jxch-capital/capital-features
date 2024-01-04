package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.utils.AppContextHolder;

import java.util.Comparator;
import java.util.List;

public interface KNNService {

    double distance(List<KLine> a, List<KLine> b);

    default List<KNeighbor> searchHasFuture(KNode futureKNode, @NonNull List<KNode> all, int size, int futureNum) {
        return all.stream()
                .map(kn -> KNeighbor.builder()
                        .dist(distance(kn.subtractLast(futureNum).getKLines(),
                                futureKNode.subtractLast(futureNum).getKLines()))
                        .kNode(kn).build())
                .sorted(Comparator.comparingDouble(KNeighbor::getDist))
                .limit(size).toList();
    }

    default List<KNeighbor> searchHasFutureNodes(KNode node, @NonNull List<KNode> all, int size, int futureNum) {
        return all.stream()
                .map(kn -> KNeighbor.builder()
                        .dist(distance(kn.subtractLast(futureNum).getKLines(),
                                node.getKLines()))
                        .kNode(kn).build())
                .sorted(Comparator.comparingDouble(KNeighbor::getDist))
                .limit(size).toList();
    }

    default List<KNeighbor> search(KNode kNode, @NonNull List<KNode> all, int size) {
        return all.stream().map(kn -> KNeighbor.builder()
                        .dist(distance(kn.getKLines(), kNode.getKLines())).kNode(kn).build())
                .sorted(Comparator.comparingDouble(KNeighbor::getDist))
                .limit(size).toList();
    }

    default List<KNeighbor> search(@NonNull KNodeParam kNodeParam, @NonNull KNodeService kNodeService, int size) {
        KNode kNode = kNodeService.current(kNodeParam);
        List<KNode> kNodes = kNodeService.comparison(kNodeParam);
        return search(kNode, kNodes, size);
    }

    default List<KNeighbor> search(@NonNull KNodeParam kNodeParam, int size) {
        return search(kNodeParam, AppContextHolder.getContext().getBean(KNodeService.class), size);
    }

    default String getName() {
        return getClass().getSimpleName();
    }

    default KNodeParam getDefaultKNodeParam() {
        return KNodeParam.builder()
                .maxLength(20)
                .size(20)
                .intervalEnum(IntervalEnum.DAY_1)
                .build();
    }

    default void setParamSupport(Object param) {

    }

}
