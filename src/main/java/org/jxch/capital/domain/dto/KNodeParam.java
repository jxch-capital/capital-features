package org.jxch.capital.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.stock.IntervalEnum;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KNodeParam implements Cloneable {
    @Builder.Default
    private String code = "SPY";
    @Builder.Default
    private int size = 20;
    @Builder.Default
    private IntervalEnum intervalEnum = IntervalEnum.DAY_1;
    @Builder.Default
    private long stockPoolId = -1;
    @Builder.Default
    private int maxLength = 0;
    @Builder.Default
    private List<IndicatorWrapper> indicatorWrappers = new ArrayList<>();
    @Builder.Default
    private Date end = Calendar.getInstance().getTime();
    @Builder.Default
    private Long indicesComId = -1L;
    @Builder.Default
    private Boolean normalized = false;

    // todo 删除 futureNum，与 KNodeService 行为不一致
    @Deprecated
    @Builder.Default
    private int futureNum = 8;

    public KNodeParam addIndicator(IndicatorWrapper indicatorWrapper) {
        this.indicatorWrappers.add(indicatorWrapper);
        return this;
    }

    public KNodeParam addIndicators(List<IndicatorWrapper> wrappers) {
        this.indicatorWrappers.addAll(wrappers);
        return this;
    }

    public Boolean hasIndicator(String name) {
        return indicatorWrappers.stream().anyMatch(indicatorWrapper -> Objects.equals(indicatorWrapper.getName(), name));
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public List<String> getIndicatorNames() {
        return indicatorWrappers.stream().map(IndicatorWrapper::getName).toList();
    }

    public boolean hasIndicatorWrappers() {
        return !indicatorWrappers.isEmpty();
    }

    public KNodeParam add(KNodeParam kNodeParam) {
        if (indicatorWrappers.isEmpty()) {
            this.indicatorWrappers = kNodeParam.getIndicatorWrappers();
        }

        if (Objects.equals(maxLength, 0)) {
            this.maxLength = kNodeParam.getMaxLength();
        }

        if (Objects.equals(code, null)) {
            this.code = kNodeParam.getCode();
        }

        if (Objects.equals(size, 0)) {
            this.size = kNodeParam.getSize();
        }

        if (Objects.equals(intervalEnum, null)) {
            this.intervalEnum = kNodeParam.getIntervalEnum();
        }

        if (Objects.equals(stockPoolId, 0)) {
            this.stockPoolId = kNodeParam.getStockPoolId();
        }

        if (!Objects.equals(end, kNodeParam.getEnd())) {
            this.end = kNodeParam.getEnd();
        }

        if (Objects.equals(futureNum, 0)) {
            this.futureNum = kNodeParam.getFutureNum();
        }

        return this;
    }

    public boolean hasIndicesComId() {
        return Objects.nonNull(indicesComId) && this.indicesComId != -1L;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KNodeParam that = (KNodeParam) o;

        if (size != that.size) return false;
        if (stockPoolId != that.stockPoolId) return false;
        if (maxLength != that.maxLength) return false;
        if (futureNum != that.futureNum) return false;
        if (!Objects.equals(code, that.code)) return false;
        if (intervalEnum != that.intervalEnum) return false;
        if (!Objects.equals(indicatorWrappers, that.indicatorWrappers))
            return false;
        return Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + size;
        result = 31 * result + (intervalEnum != null ? intervalEnum.hashCode() : 0);
        result = 31 * result + (int) (stockPoolId ^ (stockPoolId >>> 32));
        result = 31 * result + maxLength;
        result = 31 * result + (indicatorWrappers != null ? indicatorWrappers.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + futureNum;
        return result;
    }

    @Override
    public KNodeParam clone() {
        try {
            return (KNodeParam) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
