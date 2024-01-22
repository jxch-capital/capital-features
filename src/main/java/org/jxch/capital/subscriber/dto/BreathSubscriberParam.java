package org.jxch.capital.subscriber.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.subscriber.SubscriberParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreathSubscriberParam implements SubscriberParam {
    @Builder.Default
    private Integer length = 90;
}
