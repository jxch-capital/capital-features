package org.jxch.capital.learning.signal.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SignalBackTestClassifierParam extends SignalBackTestParam {
    private Long classifierModelId;
}
