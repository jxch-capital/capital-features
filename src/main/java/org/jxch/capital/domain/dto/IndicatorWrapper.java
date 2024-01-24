package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class IndicatorWrapper {
    private String name;
    private Function<BarSeries, Indicator<Num>> indicatorFunc;
    @Builder.Default
    private Boolean immutable = false;

    public Indicator<Num> getIndicator(BarSeries barSeries) {
        return indicatorFunc.apply(barSeries);
    }

}
