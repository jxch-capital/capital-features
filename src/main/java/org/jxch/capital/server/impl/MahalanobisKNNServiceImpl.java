package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.MathEx;
import smile.math.distance.MahalanobisDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MahalanobisKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;

    private double distance(double[] a, double[] b) {
        MahalanobisDistance distance = new MahalanobisDistance(MathEx.cov(new double[][]{a, b}));
        return distance.d(a, b);
    }

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (distance(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + distance(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + distance(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + distance(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "马哈拉诺比斯距离 (不支持)";
    }
}
