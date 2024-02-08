package org.jxch.capital.learning.train.param.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.train.param.TrainDataParam;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataDistillerParam implements TrainDataParam {
    private String model;
    private Long trainConfigId;
    @Builder.Default
    private Double upTh = 0.8;
    @Builder.Default
    private Double downTh = 0.2;
}