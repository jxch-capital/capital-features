package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PearsonsCorrelationKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;
    private final PearsonsCorrelation pearsons = new PearsonsCorrelation();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (pearsons.correlation(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + pearsons.correlation(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + pearsons.correlation(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + pearsons.correlation(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "皮尔逊相关系数";
    }
}
