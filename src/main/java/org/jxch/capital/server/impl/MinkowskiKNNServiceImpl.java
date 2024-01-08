package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.MinkowskiDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MinkowskiKNNServiceImpl implements KNNService {
    private final MinkowskiDistance minkowskiDistance = new MinkowskiDistance(2);


    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, minkowskiDistance::d);
    }

    @Override
    public String getName() {
        return "闵可夫斯基距离-平均";
    }

}
