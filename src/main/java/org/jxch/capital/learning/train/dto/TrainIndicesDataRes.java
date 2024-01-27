package org.jxch.capital.learning.train.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.learning.train.TrainDataRes;

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
        return kNodeTrains.getFeaturesT();
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public List<KLine> getSourceKLines() {
        return kNodeTrains.getKNodes().stream().map(kNodeTrain -> kNodeTrain.getKLines().get(kNodeTrain.getEndIndex())).toList();
    }

}
