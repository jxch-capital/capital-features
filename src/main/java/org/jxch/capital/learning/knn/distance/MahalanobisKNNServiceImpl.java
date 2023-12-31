package org.jxch.capital.learning.knn.distance;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import smile.math.MathEx;
import smile.math.distance.MahalanobisDistance;

import java.util.List;

@Slf4j
//@Service
@RequiredArgsConstructor
public class MahalanobisKNNServiceImpl implements KNNService {

    private double distance(double[] a, double[] b) {
        MahalanobisDistance distance = new MahalanobisDistance(MathEx.cov(new double[][]{a, b}));
        return distance.d(a, b);
    }

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, this::distance);
    }

    @Override
    public String getName() {
        return "马哈拉诺比斯距离-平均 (不支持)";
    }
}
