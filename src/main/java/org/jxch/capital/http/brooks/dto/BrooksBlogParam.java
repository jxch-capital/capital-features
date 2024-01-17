package org.jxch.capital.http.brooks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BrooksBlogParam {
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Boolean containsPinned = false;
}
