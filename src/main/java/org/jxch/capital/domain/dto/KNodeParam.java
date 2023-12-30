package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.server.IntervalEnum;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KNodeParam {
    private String code;
    private int size;
    private IntervalEnum intervalEnum;
    private long stockPoolId;

    @Builder.Default
    private int maxLength = 0;
    @Builder.Default
    private List<IndicatorWrapper> indicatorWrappers = new ArrayList<>();

    public KNodeParam addIndicator(IndicatorWrapper indicatorWrapper) {
        indicatorWrappers.add(indicatorWrapper);
        return this;
    }

    public boolean hasIndicator() {
        return !indicatorWrappers.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KNodeParam that = (KNodeParam) o;

        if (size != that.size) return false;
        if (stockPoolId != that.stockPoolId) return false;
        if (!code.equals(that.code)) return false;
        return intervalEnum == that.intervalEnum;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + size;
        result = 31 * result + intervalEnum.hashCode();
        result = 31 * result + (int) (stockPoolId ^ (stockPoolId >>> 32));
        return result;
    }

}
