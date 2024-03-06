package org.jxch.capital.learning.old.train.param.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.old.train.param.SignalType;
import org.jxch.capital.learning.old.train.param.TrainDataParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TrainDataCentrifugeParam implements TrainDataParam {
    private String model;
    private Long trainConfigId;
    @Builder.Default
    private String type = SignalType.UP.toString();
    @Builder.Default
    private Double upTh = 0.8;
    @Builder.Default
    private Double downTh = 0.2;

    public SignalType getEType() {
        return SignalType.valueOf(this.type);
    }

}