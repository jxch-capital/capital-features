package org.jxch.capital.utils;

import lombok.NonNull;

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

}
