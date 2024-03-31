package org.jxch.capital.chart.impl;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.Colors;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.chart.dto.StockPoolChartParam;
import org.jxch.capital.chart.dto.StockPoolChartRes;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.StockBaseDto;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.StockBaseService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.stock.StockService;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolBubbleChartServiceImpl implements ChartPngService<StockPoolBubbleChartParam, StockPoolScatterChartRes> {
    private final StockPoolService stockPoolService;
    private final StockService stockService;
    private final StockBaseService stockBaseService;

    @Override
    public StockPoolScatterChartRes chart(@NonNull StockPoolBubbleChartParam param) {
        StockPoolScatterChartRes res = new StockPoolScatterChartRes().setTimestamp(param.getTimestamp());

        List<String> poolNames = stockPoolService.findById(param.getStockPoolIds()).stream().map(StockPoolDto::getPoolName).toList();
        BubbleChart chart = new BubbleChartBuilder().width(param.getWidth()).height(param.getHeight())
                .title(String.format("%s Pool [%s]", String.join(",", poolNames), DateUtil.format(DateUtil.date(param.getTimestamp()), "yyyy-MM-dd HH:mm:ss")))
                .xAxisTitle(param.getXl() + "D%")
                .yAxisTitle(param.getYl() + "D%")
                .build();

        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendVisible(false);
        Charts.bubbleDartTheme(chart);

        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        List<Double> pData = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        for (Long stockPoolId : param.getStockPoolIds()) {
            for (String code : stockPoolService.findById(stockPoolId).getPoolStockList()) {
                if (!codes.contains(code)) {
                    List<KLine> history = stockService.history(HistoryParam.builder().start(param.getStart()).code(code).build());
                    KLine lastKLine = history.get(history.size() - 1);
                    KLine plKLine = history.get(history.size() - param.getPl());
                    KLine xlKLine = history.get(history.size() - param.getXl());
                    KLine ylKLine = history.get(history.size() - param.getYl());

                    double sl = (lastKLine.getClose() - plKLine.getClose()) / plKLine.getClose() * 100;
                    xData.add((lastKLine.getClose() - xlKLine.getClose()) / xlKLine.getClose());
                    yData.add((lastKLine.getClose() - ylKLine.getClose()) / ylKLine.getClose());
                    pData.add(param.getMinSize() + Math.abs(sl) * param.getSizeZoom());
                    colors.add(Colors.getColorFromGradientByRTG(sl, param.getColorRange()));
                    codes.add(code);
                }
            }
        }

        Map<String, StockBaseDto> stockBaseDtoMap = stockBaseService.findByCode(codes).stream().collect(Collectors.toMap(StockBaseDto::getCode, Function.identity()));
        for (int i = 0; i < xData.size(); i++) {
            BubbleSeries series = chart.addSeries(codes.get(i), Collections.singletonList(xData.get(i)), Collections.singletonList(yData.get(i)), Collections.singletonList(pData.get(i)));
            series.setFillColor(colors.get(i));
            series.setMarker(SeriesMarkers.CIRCLE);
            series.setLineColor(new Color(0, 0, 0, 0));
            String stockName = Objects.nonNull(stockBaseDtoMap.get(codes.get(i))) ? stockBaseDtoMap.get(codes.get(i)).getName() : codes.get(i);
            chart.addAnnotation(new AnnotationText(stockName, xData.get(i), yData.get(i), false));
        }

        return res.setPath(Charts.createChartPng(param.getIdentifier() + getClass().getSimpleName() + param.getTimestamp(), chart));
    }

    @Override
    public void clear(@NonNull StockPoolScatterChartRes res) {
        Charts.delete(res.getPath());
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
