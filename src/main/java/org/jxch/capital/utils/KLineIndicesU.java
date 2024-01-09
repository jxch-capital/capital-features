package org.jxch.capital.utils;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLineIndices;

import java.util.Arrays;
import java.util.List;

public class KLineIndicesU {

    public static double[] normalizedIndicesArrH(@NonNull List<KLineIndices> kLineIndices) {
        return Arrays.stream(MathU.transpose(kLineIndices.stream()
                        .map(indices -> indices.getIndices().values().stream().mapToDouble(value -> value).toArray())
                        .toArray(double[][]::new)))
                .flatMapToDouble(Arrays::stream).toArray();
    }

    public static double[] normalizedIndicesArrV(@NonNull List<KLineIndices> kLineIndices) {
        return kLineIndices.stream().flatMap(indices -> indices.getIndices().values().stream())
                .mapToDouble(value -> value)
                .toArray();
    }

}
