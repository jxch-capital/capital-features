package org.jxch.capital.learning.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.ManhattanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManhattanIndicesKNNServiceImpl implements KNNService {
    private final ManhattanDistance manhattanDistance = new ManhattanDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesH(a, b, manhattanDistance::d);
    }

    @Override
    public String getName() {
        return "曼哈顿距离-指标序列";
    }
}

