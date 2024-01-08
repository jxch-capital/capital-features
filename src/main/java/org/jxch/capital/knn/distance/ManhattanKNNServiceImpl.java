package org.jxch.capital.knn.distance;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.stereotype.Service;
import smile.math.distance.ManhattanDistance;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManhattanKNNServiceImpl implements KNNService {
    private final ManhattanDistance manhattanDistance = new ManhattanDistance();

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b) {
        return KNNs.distance(a, b, manhattanDistance::d);
    }

    @Override
    public String getName() {
        return "曼哈顿距离-K线-平均";
    }
}
