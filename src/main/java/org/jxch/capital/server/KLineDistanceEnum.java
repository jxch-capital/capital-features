package org.jxch.capital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.impl.DTWDistanceServiceImpl;
import org.jxch.capital.server.impl.LorentzianDistanceServiceImpl;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum KLineDistanceEnum {
    DTW("DTW", DTWDistanceServiceImpl.class),
    LD("LD", LorentzianDistanceServiceImpl.class),
    ;
    private final String name;
    private final Class<? extends DistanceService<? extends KLine>> distanceService;

    public static KLineDistanceEnum pares(String name) {
        return Arrays.stream(KLineDistanceEnum.values())
                .filter(KLineDistanceEnum -> Objects.equals(KLineDistanceEnum.getName(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("不支持" + name + "类型的距离计算，请检查输入是否正确"));
    }

}
