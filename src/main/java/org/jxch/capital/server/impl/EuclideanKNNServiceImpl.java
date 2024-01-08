package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.EuclideanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EuclideanKNNServiceImpl implements KNNService {
    private final EuclideanDistance euclideanDistance = new EuclideanDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, euclideanDistance::d);
    }

    @Override
    public String getName() {
        return "欧氏距离-平均";
    }
}
