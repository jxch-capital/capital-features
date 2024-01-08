package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.DynamicTimeWarping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTWKNNServiceImpl implements KNNService {

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, DynamicTimeWarping::d);
    }

    @Override
    public String getName() {
        return "DTW-平均";
    }
}
