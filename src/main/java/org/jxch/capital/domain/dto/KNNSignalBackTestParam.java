package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KNNSignalBackTestParam extends SignalBackTestParam {
    private KNNParam knnParam;
    private List<String> filters;
}
