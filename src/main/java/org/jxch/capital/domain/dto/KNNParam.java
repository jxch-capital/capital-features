package org.jxch.capital.domain.dto;

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
public class KNNParam {
    private String distanceName;
    private Integer neighborSize = 12;
    private KNodeParam kNodeParam = new KNodeParam();
}
