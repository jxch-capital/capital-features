package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SubscriberConfigDto {
    private Long id;
    private String name;
    private String service;
    private String params;
    private String remark;
}
