package org.jxch.capital.learning.old.train.param.dto;

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
public class TrainDataEMAReverseSignalFilterParam {
    @Builder.Default
    private Integer length = 20;
}
