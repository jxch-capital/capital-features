package org.jxch.capital.learning.model.dto;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Model3PredictRes {
    private List<KLineModelSignal> kLineModelSignals;
    private PredictionDataOneStockParam predictionDataOneStockParam;

    @JsonIgnore
    @JSONField(serialize = false)
    public Date getStartDate() {
        return kLineModelSignals.get(0).getKLine().getDate();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Date getEndDate() {
        return kLineModelSignals.get(kLineModelSignals.size() - 1).getKLine().getDate();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Integer getSize() {
        return kLineModelSignals.size();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public List<PredictSignalStack> getPredictSignalStacks() {
        return kLineModelSignals.stream().map(KLineModelSignal::getPredictSignalStack).toList();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public List<Double> getSignals() {
        return getPredictSignalStacks().stream().map(PredictSignalStack::getSignal).toList();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public List<KLine> getKLine() {
        return kLineModelSignals.stream().map(KLineModelSignal::getKLine).toList();
    }

    public KLineModelSignal getSignal(int index) {
        return kLineModelSignals.get(index);
    }

    public Boolean canStack(@NotNull Model3PredictRes predictRes) {
        return getPredictionDataOneStockParam().isSame(predictRes.getPredictionDataOneStockParam()) &&
                Objects.equals(predictRes.getSize(), getSize()) &&
                DateUtil.isSameDay(predictRes.getStartDate(), getStartDate()) &&
                DateUtil.isSameDay(predictRes.getEndDate(), getEndDate());
    }

    public Model3PredictRes stack(Model3PredictRes predictRes) {
        if (!canStack(predictRes)) {
            throw new IllegalArgumentException("两个预测集不可叠加");
        }

        for (int i = 0; i < kLineModelSignals.size(); i++) {
            this.getSignal(i).getPredictSignalStack().add(predictRes.getSignal(i).getPredictSignalStack());
        }

        return this;
    }

}
