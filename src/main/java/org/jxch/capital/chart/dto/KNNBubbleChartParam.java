package org.jxch.capital.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNNParam;

import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KNNBubbleChartParam implements ChartParam {
    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    @Builder.Default
    private KNNParam knnParam = new KNNParam();
    @Builder.Default
    private Double colorRange = 3.0;
    @Builder.Default
    private Integer width = 800;
    @Builder.Default
    private Integer height = 600;
    @Builder.Default
    private String identifier = "";
    @Builder.Default
    private Double minSize = 10.0;
    @Builder.Default
    private Double sizeZoom = 2.0;
}
