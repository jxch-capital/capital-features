package org.jxch.capital.chart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StockPoolScatterChartParam implements ChartParam {
    private List<Long> stockPoolIds = new ArrayList<>();
    private Date start;

    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    @Builder.Default
    private Integer xl = 20;
    @Builder.Default
    private Integer yl = 40;
    @Builder.Default
    private Integer pl = 5;
}
