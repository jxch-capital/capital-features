package org.jxch.capital.domain.dto;

import cn.hutool.core.date.DateField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RealPricePoolDashParam {
    private Long stockPoolId;
    private Integer offset;

    @Builder.Default
    private Integer pl = 5;
    @Builder.Default
    private Integer xl = 20;
    @Builder.Default
    private Integer yl = 40;

    @Builder.Default
    private DateField field = DateField.DAY_OF_YEAR;
}
