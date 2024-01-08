package org.jxch.capital.learning.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.MinkowskiDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinkowskiIndicesKNNServiceImpl implements KNNService {
    private final MinkowskiDistance minkowskiDistance = new MinkowskiDistance(2);

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesH(a, b, minkowskiDistance::d);
    }

    @Override
    public String getName() {
        return "闵可夫斯基距离-指标序列";
    }
}

