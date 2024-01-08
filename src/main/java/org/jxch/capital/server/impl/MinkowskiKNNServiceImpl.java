package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.distance.MinkowskiDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinkowskiKNNServiceImpl  implements KNNService {
    private final KLineMapper kLineMapper;
    private final MinkowskiDistance minkowskiDistance = new MinkowskiDistance(3);


    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (minkowskiDistance.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + minkowskiDistance.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + minkowskiDistance.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + minkowskiDistance.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "闵可夫斯基距离";
    }

}
