package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierModelConfigDto {
    private Long id;
    private String name;
    private Long classifierId;
    private Long stockPoolId;
    @Builder.Default
    private Integer size = 20;
    @Builder.Default
    private Integer futureNum = 6;
    @Builder.Default
    private Long indicesComId = null;
    private String remark;
    @Builder.Default
    private boolean hasLocalModel = false;

    public boolean hasIndicesCombination() {
        return Objects.nonNull(indicesComId);
    }

}
