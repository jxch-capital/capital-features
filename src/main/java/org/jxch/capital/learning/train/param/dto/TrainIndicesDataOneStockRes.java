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
import org.jxch.capital.learning.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataOneStockRes implements TrainDataRes, PredictionDataOneStockRes {
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
    public List<KLine> getSourceKLines() {
        return kNodeTrains.getKNodes().stream().map(kNodeTrain -> kNodeTrain.getKLines().get(kNodeTrain.getEndIndex())).toList();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getSignals(@NotNull SignalType signalType) {
        return switch (signalType) {
            case UP -> kNodeTrains.getUpSignals();
            case DOWN -> kNodeTrains.getDownSignals();
            case UP_DOWN, DEFAULT -> kNodeTrains.getSignals3();
        };
    }

}
