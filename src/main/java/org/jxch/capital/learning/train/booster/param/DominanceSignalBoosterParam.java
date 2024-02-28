package org.jxch.capital.learning.train.booster.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DominanceSignalBoosterParam {
    @Builder.Default
    private double th = 1.5;
    @Builder.Default
    private DominanceStrategyType dominanceStrategyType = DominanceStrategyType.PROXIMITY;
}
