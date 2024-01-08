package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PearsonsCorrelationKNNServiceImpl implements KNNService {
    private final PearsonsCorrelation pearsons = new PearsonsCorrelation();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, pearsons::correlation);
    }

    @Override
    public String getName() {
        return "皮尔逊相关系数";
    }
}
