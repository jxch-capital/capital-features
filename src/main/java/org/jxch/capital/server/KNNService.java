package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNode;

import java.util.Comparator;
import java.util.List;

public interface KNNService {

    double distance(List<KLine> a, List<KLine> b);

    default List<KNeighbor> search(KNode kNode, @NonNull List<KNode> all, int size) {
        return all.stream().map(kn -> KNeighbor.builder()
                        .dist(distance(kn.getKLines(), kNode.getKLines())).kNode(kn).build())
                .sorted(Comparator.comparingDouble(KNeighbor::getDist))
                .limit(size).toList();
    }

    default String getName() {
        return getClass().getSimpleName();
    }

}
