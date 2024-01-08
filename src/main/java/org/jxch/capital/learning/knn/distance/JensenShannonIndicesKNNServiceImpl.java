package org.jxch.capital.learning.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.JensenShannonDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JensenShannonIndicesKNNServiceImpl implements KNNService {
    private final JensenShannonDistance jensenShannonDistance = new JensenShannonDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesH(a, b, jensenShannonDistance::d);
    }

    @Override
    public String getName() {
        return "詹森香农距离-指标序列";
    }
}

