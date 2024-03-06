package org.jxch.capital.learning.old.train.param.dto;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.old.train.param.SignalType;
import org.jxch.capital.learning.old.train.param.TrainDataRes;
import org.jxch.capital.utils.CollU;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TrainDataCentrifugeRes implements TrainDataRes {
    private List<double[][]> targetFeatures = new ArrayList<>();
    private List<Integer> targetSignals = new ArrayList<>();
    private List<double[][]> oppositeFeatures = new ArrayList<>();
    private List<Integer> oppositeSignals = new ArrayList<>();

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public double[][][] getFeatures() {
        return CollU.toDoubleArr3(targetFeatures);
    }

    @Override
    @JsonIgnore
    @JSONField(serialize = false)
    public int[] getSignals(SignalType signalType) {
        if (Objects.equals(signalType, SignalType.DEFAULT)) {
            return ArrayUtil.addAll(CollU.toIntArr1(targetSignals), CollU.toIntArr1(oppositeSignals));
        }

        throw new IllegalArgumentException("不支持这种类型的信号：" + signalType);
    }

}
