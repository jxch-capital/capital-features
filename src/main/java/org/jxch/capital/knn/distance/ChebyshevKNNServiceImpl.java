package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.knn.KNNService;
import org.jxch.capital.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.ChebyshevDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChebyshevKNNServiceImpl implements KNNService {
    private final ChebyshevDistance chebyshevDistance = new ChebyshevDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, chebyshevDistance::d);
    }

    @Override
    public String getName() {
        return "切比雪夫距离-平均";
    }
}
