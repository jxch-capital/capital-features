package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.distance.JensenShannonDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JensenShannonKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;
    private final JensenShannonDistance jensenShannonDistance = new JensenShannonDistance();

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (jensenShannonDistance.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + jensenShannonDistance.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + jensenShannonDistance.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + jensenShannonDistance.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public String getName() {
        return "詹森香农距离";
    }
}
