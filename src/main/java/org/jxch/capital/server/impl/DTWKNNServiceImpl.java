package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNodeService;
import org.springframework.stereotype.Service;
import smile.math.distance.DynamicTimeWarping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTWKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (DynamicTimeWarping.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + DynamicTimeWarping.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + DynamicTimeWarping.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + DynamicTimeWarping.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

}
