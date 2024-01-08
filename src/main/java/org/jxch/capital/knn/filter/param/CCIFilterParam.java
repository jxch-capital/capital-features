package org.jxch.capital.knn.filter.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.ta4j.core.indicators.CCIIndicator;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CCIFilterParam implements FilterParam {
    @Builder.Default
    private int length = 20;
    @Builder.Default
    private Duration duration = Duration.ofDays(1);
    @Builder.Default
    private double limitAbs = 140;

    @Override
    public IndicatorWrapper wrapper() {
        return IndicatorWrapper.builder()
                .name(name())
                .indicatorFunc(barSeries -> new CCIIndicator(barSeries, length))
                .build();
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @Override
    public String name() {
        return "CCI-" + length;
    }

}
