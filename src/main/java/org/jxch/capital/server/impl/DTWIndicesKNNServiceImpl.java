package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.utils.MathU;
import org.springframework.stereotype.Service;
import smile.math.distance.DynamicTimeWarping;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DTWIndicesKNNServiceImpl implements KNNService {

    private double[][] indices(@NonNull List<KLineIndices> indices) {
        return MathU.transpose(indices.stream().map(kLineIndices ->
                        kLineIndices.getIndices().values().stream().mapToDouble(value -> value).toArray())
                .toArray(double[][]::new));
    }

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b) {
        double[][] aIndicesArr = indices(a.stream().map(kLine -> (KLineIndices) kLine).toList());
        double[][] bIndicesArr = indices(b.stream().map(kLine -> (KLineIndices) kLine).toList());

        return IntStream.range(0, aIndicesArr.length)
                .mapToDouble(i -> DynamicTimeWarping.d(aIndicesArr[i], bIndicesArr[i]))
                .average().orElseThrow();
    }

    @Override
    public String getName() {
        return "DTW-指标序列-横向";
    }

}
