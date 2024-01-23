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
public class KNNBubbleChartRes implements ChartRes {
    private String path;
    private Long timestamp = Calendar.getInstance().getTime().getTime();
}
