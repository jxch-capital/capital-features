package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;
import smile.math.distance.ManhattanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManhattanKNNServiceImpl implements KNNService {
    private final KLineMapper kLineMapper;
    private final ManhattanDistance manhattanDistance = new ManhattanDistance();

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("要对比的K线序列长度必须一致");
        }

        return (manhattanDistance.d(kLineMapper.toCloseArr(a), kLineMapper.toCloseArr(b))
                + manhattanDistance.d(kLineMapper.toOpenArr(a), kLineMapper.toOpenArr(b))
                + manhattanDistance.d(kLineMapper.toHighArr(a), kLineMapper.toHighArr(b))
                + manhattanDistance.d(kLineMapper.toLowArr(a), kLineMapper.toLowArr(b))) / 4;
    }

}
