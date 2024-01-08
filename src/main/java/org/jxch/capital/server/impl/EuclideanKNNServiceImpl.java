package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.distance.EuclideanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuclideanKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;
    private final EuclideanDistance euclideanDistance = new EuclideanDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (euclideanDistance.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + euclideanDistance.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + euclideanDistance.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + euclideanDistance.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "欧氏距离";
    }
}
