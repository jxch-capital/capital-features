package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PearsonsCorrelationIndicesAKNNServiceImpl implements KNNService {
    private final PearsonsCorrelation pearsons = new PearsonsCorrelation();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesHAverage(a, b, pearsons::correlation);
    }

    @Override
    public String getName() {
        return "皮尔逊相关系数-指标序列-平均";
    }
}
