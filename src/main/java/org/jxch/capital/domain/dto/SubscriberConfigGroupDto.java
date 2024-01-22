package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class SubscriberConfigGroupDto {
    private Long id;
    private String name;
    private String groupService;
    private List<Long> subscriberConfigIds;
    private String remark;
}
