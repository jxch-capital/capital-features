package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.JensenShannonDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JensenShannonKNNServiceImpl implements KNNService {
    private final JensenShannonDistance jensenShannonDistance = new JensenShannonDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return KNNs.distance(a, b, jensenShannonDistance::d);
    }

    @Override
    public String getName() {
        return "詹森香农距离-平均";
    }
}
