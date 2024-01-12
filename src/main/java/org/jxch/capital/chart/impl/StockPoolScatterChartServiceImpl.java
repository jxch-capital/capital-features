package org.jxch.capital.chart.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.dto.StockPoolChartParam;
import org.jxch.capital.chart.dto.StockPoolChartRes;
import org.jxch.capital.chart.dto.StockPoolScatterChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.server.StockService;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolScatterChartServiceImpl implements ChartPngService<StockPoolScatterChartParam, StockPoolScatterChartRes> {
    private final StockPoolService stockPoolService;
    private final StockService stockService;

    @Override
    public StockPoolScatterChartRes chart(@NonNull StockPoolScatterChartParam param) {
        StockPoolScatterChartRes res = new StockPoolScatterChartRes().setTimestamp(param.getTimestamp());

        XYChart chart = new XYChartBuilder().width(800).height(600)
                .xAxisTitle("中期").yAxisTitle("长期").build();
        chart.getStyler().setLegendVisible(false);


        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        List<Double> pData = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        for (Long stockPoolId : param.getStockPoolIds()) {
            for (String code : stockPoolService.findById(stockPoolId).getPoolStockList()) {
                if (!codes.contains(code)) {
                    codes.add(code);

                    List<KLine> history = stockService.history(HistoryParam.builder().start(param.getStart()).code(code).build());

                    KLine lastKLine = history.get(history.size() - 1);
                    KLine plKLine = history.get(history.size() - param.getPl());
                    KLine xlKLine = history.get(history.size() - param.getXl());
                    KLine ylKLine = history.get(history.size() - param.getYl());

                    xData.add((lastKLine.getClose() - xlKLine.getClose()) / xlKLine.getClose());
                    yData.add((lastKLine.getClose() - ylKLine.getClose()) / ylKLine.getClose());
                    pData.add((lastKLine.getClose() - plKLine.getClose()) / plKLine.getClose());
                }

            }
        }

        XYSeries series = chart.addSeries("Data", xData, yData, pData);
        series.setMarker(SeriesMarkers.CIRCLE);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);


        String pngPath = Charts.createChartPng(getClass().getSimpleName() + param.getTimestamp(), chart);
        return res.setPath(pngPath);
    }

    @Override
    public void clear(@NonNull StockPoolScatterChartRes res) {

    }

    private StockPoolChartRes miniLine(@NonNull StockPoolChartParam param) {
        StockPoolChartRes res = new StockPoolChartRes().setTimestamp(param.getTimestamp());

        for (Long stockPoolId : param.getStockPoolIds()) {
            for (String code : stockPoolService.findById(stockPoolId).getPoolStockList()) {
                List<KLine> history = stockService.history(HistoryParam.builder().code(code).start(param.getStart()).build());
                param.addStock(stockPoolId, code, history.stream().map(KLine::getClose).toList());
            }
        }

        for (Map.Entry<Long, Map<String, XYChart>> stockPoolEntry : param.getCharts().entrySet()) {
            for (Map.Entry<String, XYChart> entry : stockPoolEntry.getValue().entrySet()) {
                res.addPath(stockPoolEntry.getKey(), entry.getKey(),
                        Charts.createChartPng(stockPoolEntry.getKey() + entry.getKey() + param.getTimestamp(), entry.getValue()));
            }
        }

        return res;
    }

}
