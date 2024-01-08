package org.jxch.capital.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.knn.KNNService;
import org.jxch.capital.knn.KNNs;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosIndicesKNNServiceImpl implements KNNService {
    private double distance(double[] a, double[] b) {
        return new ArrayRealVector(a).cosine(new ArrayRealVector(b));
    }

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distanceIndicesH(a, b, this::distance);
    }

    @Override
    public String getName() {
        return "余弦相似度-指标序列";
    }
}
