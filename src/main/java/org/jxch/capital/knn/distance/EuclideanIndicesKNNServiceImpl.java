package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.knn.KNNService;
import org.jxch.capital.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.EuclideanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuclideanIndicesKNNServiceImpl implements KNNService {
    private final EuclideanDistance euclideanDistance = new EuclideanDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesH(a, b, euclideanDistance::d);
    }

    @Override
    public String getName() {
        return "欧氏距离-指标序列";
    }
}
