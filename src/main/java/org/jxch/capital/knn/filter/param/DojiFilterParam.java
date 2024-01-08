package org.jxch.capital.knn.filter.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.IndicatorWrapper;

import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DojiFilterParam implements FilterParam {
    @Builder.Default
    private double percent = (double) 1 / 6;
    @Builder.Default
    private Duration duration = Duration.ofDays(1);

    @Override
    public IndicatorWrapper wrapper() {
        throw new UnsupportedOperationException("不需要进行指标计算");
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @Override
    public String name() {
        return "Doji";
    }

}
