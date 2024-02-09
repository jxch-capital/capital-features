package org.jxch.capital.learning.train.param.dto;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TrainDataCentrifugeRes implements TrainDataRes {
    private double[][][] features;
    private int[] signals;
    private double[][][] oppositeFeatures;
    private int[] oppositeSignals;

    @Override
    public int[] getSignals(SignalType signalType) {
        if (Objects.equals(signalType, SignalType.DEFAULT)) {
            return ArrayUtil.addAll(signals, oppositeSignals);
        }

        throw new IllegalArgumentException("不支持这种类型的信号：" + signalType);
    }

}
