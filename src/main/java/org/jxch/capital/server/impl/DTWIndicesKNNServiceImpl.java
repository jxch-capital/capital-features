package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.springframework.stereotype.Service;
import smile.math.distance.DynamicTimeWarping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DTWIndicesKNNServiceImpl implements KNNService {

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b) {
        return KNNs.distanceIndicesHAverage(a, b, DynamicTimeWarping::d);
    }

    @Override
    public String getName() {
        return "DTW-指标序列-平均";
    }

}
