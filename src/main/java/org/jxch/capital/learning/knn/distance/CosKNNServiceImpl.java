package org.jxch.capital.learning.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosKNNServiceImpl implements KNNService {

    private double distance(double[] a, double[] b) {
        return new ArrayRealVector(a).cosine(new ArrayRealVector(b));
    }

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, this::distance);
    }

    @Override
    public String getName() {
        return "余弦相似度-K线-平均";
    }
}
