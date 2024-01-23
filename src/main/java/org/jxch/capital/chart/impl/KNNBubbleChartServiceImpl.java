package org.jxch.capital.chart.impl;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.Charts;
import org.jxch.capital.chart.Colors;
import org.jxch.capital.chart.dto.KNNBubbleChartParam;
import org.jxch.capital.chart.dto.KNNBubbleChartRes;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.KNNAutoService;
import org.jxch.capital.server.KNodeAnalyzeService;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.StockPoolService;
import org.knowm.xchart.AnnotationText;
import org.knowm.xchart.BubbleChart;
import org.knowm.xchart.BubbleChartBuilder;
import org.knowm.xchart.BubbleSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNNBubbleChartServiceImpl implements ChartPngService<KNNBubbleChartParam, KNNBubbleChartRes> {
    private final KNNAutoService knnAutoService;
    private final KNodeAnalyzeService kNodeAnalyzeService;
    private final StockPoolService stockPoolService;
    private final KNodeService kNodeService;

    @Override
    public KNNBubbleChartRes chart(@NonNull KNNBubbleChartParam param) {
        KNNParam knnParam = param.getKnnParam();
        List<KNeighbor> neighbors = knnAutoService.search(knnParam.getDistanceName(), knnParam.getKNodeParam(), knnParam.getNeighborSize());
        KLineAnalyzeStatistics statistics = kNodeAnalyzeService.statisticsKNN(neighbors, knnParam);

        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();
        List<Double> pData = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        Double maxDist = neighbors.stream().map(KNeighbor::getDist).max(Double::compareTo).orElseThrow();
        List<KLineAnalyzes> kLineAnalyzes = statistics.getAnalyzes().stream().filter(KLineAnalyzes::isHasFutureSignal).toList();
        for (int i = 0; i < kLineAnalyzes.size(); i++) {
            KLineAnalyzes item = kLineAnalyzes.get(i);
            List<Double> closeArr = item.getCloseArr();
            xData.add((closeArr.get(item.getEndIndex()) - closeArr.get(item.getStartIndex())) / closeArr.get(item.getStartIndex()) * 100);
            yData.add((closeArr.get(item.getFutureIndex()) - closeArr.get(item.getEndIndex())) / closeArr.get(item.getEndIndex()) * 100);
            pData.add((maxDist - neighbors.get(i).getDist()) / neighbors.get(i).getDist() * 100 * param.getSizeZoom() + param.getMinSize());
            colors.add(Colors.getColorFromGradientByRTG(item.getFuturePercent(), param.getColorRange()));
        }

        BubbleChart chart = new BubbleChartBuilder().width(param.getWidth()).height(param.getHeight())
                .title(String.format("%s[%s/%.2f%%]: %s-%s (%s:%s-%s) [%s]", knnParam.getKNodeParam().getCode(),
                        statistics.getFutureSignal(), 100.0 * statistics.getFutureSignal() / neighbors.size(),
                        stockPoolService.findById(knnParam.getKNodeParam().getStockPoolId()).getPoolName(), knnParam.getDistanceName(),
                        knnParam.getKNodeParam().getSize(), knnParam.getKNodeParam().getFutureNum(), knnParam.getNeighborSize(),
                        DateUtil.format(DateUtil.date(param.getTimestamp()), "yyyy-MM-dd HH:mm:ss")))
                .xAxisTitle("过去%")
                .yAxisTitle("未来%")
                .build();

        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendVisible(false);
        KNNBubbleChartRes res = new KNNBubbleChartRes().setTimestamp(param.getTimestamp());

        for (int i = 0; i < xData.size(); i++) {
            BubbleSeries series = chart.addSeries("Neighbor" + i, Collections.singletonList(xData.get(i)), Collections.singletonList(yData.get(i)), Collections.singletonList(pData.get(i)));
            series.setFillColor(colors.get(i));
            series.setMarker(SeriesMarkers.CIRCLE);
            series.setLineColor(new Color(0, 0, 0, 0));
            chart.addAnnotation(new AnnotationText(String.format("%s-%s", neighbors.get(i).getKNode().getCode(), DateUtil.format(neighbors.get(i).getKNode().getFristKLine().getDate(), "yyMMdd")),
                    xData.get(i), yData.get(i), false));
        }

        KNodeParam currentKNodeParam = knnParam.getKNodeParam().clone().setSize(knnParam.getKNodeParam().getMaxLength() + knnParam.getKNodeParam().getSize());
        List<KLine> kLines = kNodeService.current(currentKNodeParam).getKLines();
        double startX = 100 * (kLines.get(kLines.size() - 1).getClose() - kLines.get(kLines.size() - knnParam.getKNodeParam().getSize()).getClose()) / kLines.get(kLines.size() - knnParam.getKNodeParam().getSize()).getClose();
        BubbleSeries currentSeries = chart.addSeries(knnParam.getKNodeParam().getCode(), Collections.singletonList(startX), Collections.singletonList(0), Collections.singletonList(10));
        currentSeries.setFillColor(Color.BLUE);
        currentSeries.setMarker(SeriesMarkers.CIRCLE);
        currentSeries.setLineColor(new Color(0, 0, 0, 0));
        chart.addAnnotation(new AnnotationText(String.format("%s-%.2f%%", knnParam.getKNodeParam().getCode(), startX),
                startX, 0, false));

        return res.setPath(Charts.createChartPng(param.getIdentifier() + getClass().getSimpleName() + param.getTimestamp(), chart));
    }

    @Override
    public void clear(@NonNull KNNBubbleChartRes res) {
        Charts.delete(res.getPath());
    }

}
