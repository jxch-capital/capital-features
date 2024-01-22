package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SubscriberConfigGroupDto {
    private Long id;
    private String name;
    private String subscriberType;
    private String subscriberConfigIds;
    private String remark;
}
