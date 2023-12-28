package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNodeService;
import org.springframework.stereotype.Service;
import smile.math.distance.DynamicTimeWarping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTWKNNServiceImpl implements KNNService, KNodeService {
    private final KLineMapper kLineMapper;
    private final KNodeService kNodeService;

    @Override
    public double distance(List<KLine> a, List<KLine> b) {
        return (DynamicTimeWarping.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + DynamicTimeWarping.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + DynamicTimeWarping.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + DynamicTimeWarping.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

    @Override
    public KNode current(String code, int size, @NonNull IntervalEnum intervalEnum) {
        return kNodeService.current(code, size, intervalEnum);
    }

    @Override
    public List<KNode> comparison(long stockPoolId, int size) {
        return kNodeService.comparison(stockPoolId, size);
    }

}
