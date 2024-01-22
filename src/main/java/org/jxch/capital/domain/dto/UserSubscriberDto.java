package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class UserSubscriberDto {
    private Long id;
    private Long userId;
    private Long subscriberConfigGroupId;
    private String cron;
    private Date lastSubscribeTime;
    private String remark;
}
