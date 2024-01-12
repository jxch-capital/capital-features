package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NKnnSignalParam {
    private List<Long> configIds;
    private String code;
    private String stackName;
    @Builder.Default
    private Integer signalLimitAbs = 5;
    @Builder.Default
    private List<String> filters = null;
    @Builder.Default
    private Integer futureSize = null;

    public boolean needResetFutureSize() {
        return Objects.nonNull(futureSize) && futureSize > 0;
    }

}
