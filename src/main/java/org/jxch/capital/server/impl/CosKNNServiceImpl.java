package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CosKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;

    private double distance(double[] a, double[] b) {
        RealVector vector1 = new ArrayRealVector(a);
        RealVector vector2 = new ArrayRealVector(b);
        return vector1.cosine(vector2);
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
        return "余弦相似度";
    }
}
