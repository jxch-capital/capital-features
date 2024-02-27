package org.jxch.capital.chart.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.dto.KLineChartParam;
import org.jxch.capital.chart.dto.KLineChartRes;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.utils.KLines;
import org.knowm.xchart.OHLCChart;
import org.knowm.xchart.OHLCChartBuilder;
import org.knowm.xchart.OHLCSeries;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class KLineChartServiceImpl implements ChartPngService<KLineChartParam, KLineChartRes> {
    private final StockService stockService;

    @Override
    public KLineChartRes chart(@NotNull KLineChartParam param) {
        KLineChartRes res = new KLineChartRes().setTimestamp(param.getTimestamp());
        List<KLine> kLines = stockService.history(param.getHistoryParam());

        OHLCChart chart = new OHLCChartBuilder()
                .width(param.getWidth())
                .height(param.getHeight())
                .build();
        chart.getStyler().setYAxisTicksVisible(false).setXAxisTicksVisible(false);
        chart.getStyler().setPlotBorderColor(Color.BLACK).setPlotBorderVisible(false).setChartTitleBoxVisible(false).setLegendVisible(false);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255, 60));
        chart.getStyler().setChartFontColor(Color.GRAY);
        chart.getStyler().setAxisTickLabelsColor(Color.GRAY);

        OHLCSeries ohlcSeries = chart.addSeries(param.getHistoryParam().getCode(), IntStream.range(0, KLines.getKLineItem(kLines, KLine::getDate).size()).boxed().toList(),
                KLines.getKLineItem(kLines, KLine::getOpen), KLines.getKLineItem(kLines, KLine::getHigh), KLines.getKLineItem(kLines, KLine::getLow), KLines.getKLineItem(kLines, KLine::getClose));
        ohlcSeries.setUpColor(new Color(159, 241, 114, 216));
        ohlcSeries.setDownColor(new Color(246, 105, 87, 216));

        return res.setPath(Charts.createChartPng(param.getIdentifier() + getClass().getSimpleName() + param.getTimestamp(), chart));
    }

    @Override
    public void clear(@NotNull KLineChartRes res) {
        Charts.delete(res.getPath());
    }

}
