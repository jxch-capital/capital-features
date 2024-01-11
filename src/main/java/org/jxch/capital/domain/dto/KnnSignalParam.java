package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KnnSignalParam {
    private Long configId;
    private String code;
    @Builder.Default
    private Integer signalLimitAbs = 5;
    @Builder.Default
    private List<String> filters = null;
}
