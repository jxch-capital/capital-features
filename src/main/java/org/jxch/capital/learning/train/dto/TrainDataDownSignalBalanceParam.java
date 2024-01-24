package org.jxch.capital.learning.train.dto;

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
public class TrainDataDownSignalBalanceParam {
    @Builder.Default
    private Double threshold = 0.5;
}
