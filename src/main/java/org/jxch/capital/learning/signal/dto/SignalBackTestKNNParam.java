package org.jxch.capital.learning.signal.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNNParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SignalBackTestKNNParam extends SignalBackTestParam {
    private KNNParam knnParam;
    private Integer futureNum = 4;
}
