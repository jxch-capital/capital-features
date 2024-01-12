package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KLineAnalyzeStatistics {
    private List<KLineAnalyzes> analyzes;

    @Builder.Default
    private int futureSignal = 0;

    public KLineAnalyzeStatistics statistics() {
        this.futureSignal = analyzes.stream().mapToInt(KLineAnalyzes::getFutureSignal).sum();
        return this;
    }

    public KLineAnalyzeStatistics subtractNonFuture() {
        analyzes = analyzes.stream().filter(KLineAnalyzes::isHasFutureSignal).toList();
        return this;
    }

}
