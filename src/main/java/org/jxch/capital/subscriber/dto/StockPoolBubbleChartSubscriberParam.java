package org.jxch.capital.subscriber.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.subscriber.SubscriberParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class StockPoolBubbleChartSubscriberParam implements SubscriberParam {
    @Builder.Default
    private StockPoolBubbleChartParam bubbleChartParam = new StockPoolBubbleChartParam();
}
