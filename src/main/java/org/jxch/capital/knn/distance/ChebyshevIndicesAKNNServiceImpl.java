package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.stereotype.Service;
import smile.math.distance.ChebyshevDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChebyshevIndicesAKNNServiceImpl implements KNNService {

    private final ChebyshevDistance chebyshevDistance = new ChebyshevDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesHAverage(a, b, chebyshevDistance::d);
    }

    @Override
    public String getName() {
        return "切比雪夫距离-指标序列-平均";
    }
}
