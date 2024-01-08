package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.knn.KNNService;
import org.jxch.capital.knn.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.JensenShannonDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JensenShannonKNNServiceImpl implements KNNService {
    private final JensenShannonDistance jensenShannonDistance = new JensenShannonDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, jensenShannonDistance::d);
    }

    @Override
    public String getName() {
        return "詹森香农距离-平均";
    }
}
