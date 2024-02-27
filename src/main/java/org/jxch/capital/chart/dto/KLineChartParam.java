package org.jxch.capital.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.HistoryParam;

import java.util.Calendar;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class KLineChartParam implements ChartParam {
    private HistoryParam historyParam;
    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    @Builder.Default
    private Integer width = 2560;
    @Builder.Default
    private Integer height = 1440;
    @Builder.Default
    private String identifier = "";
}
