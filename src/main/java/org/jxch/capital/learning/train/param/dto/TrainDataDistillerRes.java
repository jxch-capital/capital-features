package org.jxch.capital.learning.train.param.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataDistillerRes implements TrainDataRes {
    private double[][][] features;
    private int[] signals;

    @Override
    public double[][][] getFeatures() {
        return features;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getSignals(@NotNull SignalType signalType) {
        if (Objects.equals(signalType, SignalType.DEFAULT)) {
            return signals;
        }

        throw new IllegalArgumentException("不支持这种类型的信号：" + signalType);
    }

}
