package org.jxch.capital.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BrooksBlogChartParam implements ChartParam {
    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    @Builder.Default
    private String identifier = "";
}
