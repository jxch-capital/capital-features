package org.jxch.capital.chart.dto;

import lombok.*;
import lombok.experimental.Accessors;
import org.jxch.capital.chart.Charts;
import org.knowm.xchart.XYChart;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StockPoolChartParam implements ChartParam {
    @Builder.Default
    private Map<Long, Map<String, XYChart>> charts = new HashMap<>();
    @Builder.Default
    private List<Long> stockPoolIds = new ArrayList<>();
    @Builder.Default
    private Long timestamp = Calendar.getInstance().getTime().getTime();
    private Date start;

    public StockPoolChartParam addStock(Long stockPoolId, String stock, @NonNull List<Double> value) {
        charts.putIfAbsent(stockPoolId, new HashMap<>());
        charts.get(stockPoolId).put(stock, Charts.miniLineXYChart(stock, value));
        return this;
    }

    public StockPoolChartParam addStockPoolId(Long id) {
        this.stockPoolIds.add(id);
        return this;
    }

    public StockPoolChartParam addStockPoolId(List<Long> ids) {
        this.stockPoolIds.addAll(ids);
        return this;
    }

}
