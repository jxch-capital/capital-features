package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.distance.ChebyshevDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChebyshevKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;
    private final ChebyshevDistance chebyshevDistance = new ChebyshevDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (chebyshevDistance.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + chebyshevDistance.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + chebyshevDistance.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + chebyshevDistance.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "切比雪夫距离";
    }
}
