package org.jxch.capital.chart.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
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
public class StockPoolBubbleChartParam implements ChartParam {
    private List<Long> stockPoolIds = new ArrayList<>();

    @Builder.Default
    private Date start = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3);
    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    @Builder.Default
    private Integer xl = 60;
    @Builder.Default
    private Integer yl = 120;
    @Builder.Default
    private Integer pl = 20;
    @Builder.Default
    private Double colorRange = 20.0;
    @Builder.Default
    private Double minSize = 10.0;
    @Builder.Default
    private Double sizeZoom = 4.0;
    @Builder.Default
    private Integer width = 800;
    @Builder.Default
    private Integer height = 600;
    @Builder.Default
    private String identifier = "";
}
