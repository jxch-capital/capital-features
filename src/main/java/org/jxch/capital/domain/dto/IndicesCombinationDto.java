package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IndicesCombinationDto {
    private Long id;
    private String name;
    private String indicesIds;
    private Integer maxLength;
    private String remark;

    public List<Long> getIndicesIdList() {
        return Arrays.stream(indicesIds.split(",")).map(Long::valueOf).toList();
    }

}
