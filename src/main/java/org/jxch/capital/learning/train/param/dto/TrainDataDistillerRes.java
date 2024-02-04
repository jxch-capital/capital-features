package org.jxch.capital.learning.train.param.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataDistillerRes implements TrainDataRes {
    private double[][][] features;
    private int[] signals;
    private String signalType;

    @Override
    public double[][][] getFeatures() {
        return features;
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getUpSignals() {
        if (Objects.equals(SignalType.valueOf(signalType), SignalType.UP)) {
            return signals;
        }

        throw new UnsupportedOperationException("不支持上涨类型的信号：" + signalType);
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getDownSignals() {
        if (Objects.equals(SignalType.valueOf(signalType), SignalType.DOWN)) {
            return signals;
        }

        throw new UnsupportedOperationException("不支持下跌类型的信号：" + signalType);
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] get3Signals() {
        if (Objects.equals(SignalType.valueOf(signalType), SignalType.UP_DOWN)) {
            return signals;
        }

        throw new UnsupportedOperationException("不支持涨跌类型的信号：" + signalType);
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public List<KLine> getSourceKLines() {
        throw new UnsupportedOperationException("蒸馏器暂不支持直接获取股票K线数据");
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getSignals(@NotNull SignalType signalType) {
        return switch (signalType) {
            case UP -> getUpSignals();
            case DOWN -> getDownSignals();
            case UP_DOWN -> get3Signals();
        };
    }

}
