package org.jxch.capital.knn.filter.param;

import lombok.*;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.util.function.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EMAFilterParam implements FilterParam {
    @Builder.Default
    private int length = 20;
    @Builder.Default
    private Function<BarSeries, Indicator<Num>> priceFunc = ClosePriceIndicator::new;
    @Builder.Default
    private Duration duration = Duration.ofDays(1);

    @Override
    public IndicatorWrapper wrapper() {
        return IndicatorWrapper.builder()
                .name(name())
                .indicatorFunc(barSeries -> new EMAIndicator(priceFunc.apply(barSeries), length))
                .build();
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @Override
    public String name() {
        return "EMA-" + length;
    }

}
