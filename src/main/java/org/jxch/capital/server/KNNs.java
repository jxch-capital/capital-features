package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.utils.MathU;

import java.util.List;
import java.util.stream.IntStream;

public class KNNs {

    public static List<String> getAllKNNServicesName() {
        return AppContextHolder.getContext().getBeansOfType(KNNService.class).values()
                .stream().map(KNNService::getName).toList();
    }

    public static KNNService getKNNService(String name) {
        return AppContextHolder.getContext().getBeansOfType(KNNService.class)
                .values().stream()
                .filter(service -> service.getName().equals(name))
                .findAny()
                .orElseThrow();
    }

    public static double distance(List<KLine> a, List<KLine> b, @NonNull DistanceFunc func) {
        KLineMapper kLineMapper = KLineMapper.K_LINE_MAPPER;
        return (func.d(MathU.normalized(kLineMapper.toCloseArr(a)), MathU.normalized(kLineMapper.toCloseArr(b)))
                + func.d(MathU.normalized(kLineMapper.toOpenArr(a)), MathU.normalized(kLineMapper.toOpenArr(b)))
                + func.d(MathU.normalized(kLineMapper.toHighArr(a)), MathU.normalized(kLineMapper.toHighArr(b)))
                + func.d(MathU.normalized(kLineMapper.toLowArr(a)), MathU.normalized(kLineMapper.toLowArr(b)))) / 4;
    }

    public static double[][] indicesH(@NonNull List<KLineIndices> indices) {
        return MathU.transpose(indices.stream().map(kLineIndices ->
                        kLineIndices.getIndices().values().stream().mapToDouble(value -> value).toArray())
                .toArray(double[][]::new));
    }

    public static double distanceIndicesH(@NonNull List<KLine> a, @NonNull List<KLine> b, @NonNull DistanceFunc func) {
        double[][] aIndicesArr = indicesH(a.stream().map(kLine -> (KLineIndices) kLine).toList());
        double[][] bIndicesArr = indicesH(b.stream().map(kLine -> (KLineIndices) kLine).toList());

        return IntStream.range(0, aIndicesArr.length)
                .mapToDouble(i -> func.d(aIndicesArr[i], bIndicesArr[i]))
                .average().orElseThrow();
    }

    @FunctionalInterface
    public interface DistanceFunc {
        double d(double[] a, double[] b);
    }

}
