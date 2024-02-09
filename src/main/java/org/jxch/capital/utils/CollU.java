package org.jxch.capital.utils;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class CollU {

    public static <T, R> void alignmentMapList(@NonNull Map<T, List<R>> map) {
        int minSize = map.values().stream()
                .mapToInt(List::size)
                .min()
                .orElse(0);

        // 从每个列表的末尾开始裁剪，使其长度与最小长度一致
        for (List<R> list : map.values()) {
            list.subList(0, list.size() - minSize).clear();
        }

    }

    @NotNull
    @Contract("_, _ -> param1")
    public static <T> List<T> append(@NotNull List<T> list, T item) {
        list.add(item);
        return list;
    }

    public static <T> T last(@NotNull List<T> list) {
        return list.get(list.size() - 1);
    }


    public static int[] toIntArr1(@NotNull List<Integer> integers) {
        return integers.stream().mapToInt(v -> v).toArray();
    }

    @NotNull
    public static double[][][] toDoubleArr3(@NotNull List<double[][]> doubles) {
        double[][][] arr3 = new double[doubles.size()][][];
        for (int i = 0; i < doubles.size(); i++) {
            arr3[i] = doubles.get(i);
        }
        return arr3;
    }

}
