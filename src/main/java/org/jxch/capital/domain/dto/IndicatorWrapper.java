package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndicatorWrapper {
    private String name;
    private Function<BarSeries, Indicator<Num>> indicatorFunc;

    public Indicator<Num> getIndicator(BarSeries barSeries) {
        return indicatorFunc.apply(barSeries);
    }

}
