package org.jxch.capital.learning.train.filter.param;

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
public class KNodeTrainsFilterParam {
    @Builder.Default
    private double minTh = 2;
    @Builder.Default
    private double maxTh = 5;
}
