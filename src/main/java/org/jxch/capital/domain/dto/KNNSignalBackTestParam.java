package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KNNSignalBackTestParam extends SignalBackTestParam {
    private KNNParam knnParam;
}
