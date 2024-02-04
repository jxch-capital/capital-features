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
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataRes implements TrainDataRes {
    private KNodeTrains kNodeTrains;

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public double[][][] getFeatures() {
        return kNodeTrains.getFeatures();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getUpSignals() {
        return kNodeTrains.getUpSignals();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getDownSignals() {
        return kNodeTrains.getDownSignals();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] get3Signals() {
        return kNodeTrains.getSignals3();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public List<KLine> getSourceKLines() {
        return kNodeTrains.getKNodes().stream().map(kNodeTrain -> kNodeTrain.getKLines().get(kNodeTrain.getEndIndex())).toList();
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
