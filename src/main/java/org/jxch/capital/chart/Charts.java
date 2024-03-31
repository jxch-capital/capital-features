package org.jxch.capital.chart;

import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.knowm.xchart.*;
import org.knowm.xchart.internal.chartpart.Chart;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
public class Charts {
    @Setter
    private static String pngPath;
    @Setter
    private static String suffix;

    @NonNull
    @SneakyThrows
    public static String createChartPng(String name, @NonNull Chart<?, ?> chart) {
        String filePathName = pngPath + name + UUID.randomUUID();
        BitmapEncoder.saveBitmap(chart, filePathName, BitmapEncoder.BitmapFormat.PNG);
        return filePathName + ".png";
    }

    @NonNull
    @SneakyThrows
    public static String createAwtPng(String name, BufferedImage image) {
        String filePath = pngPath + name + UUID.randomUUID() + ".png";
        ImageIO.write(image, "png", new File(filePath));
        return filePath;
    }

    public static void delete(String filePath) {
        File file = new File(filePath);
        if (file.delete()) {
            log.debug("删除文件：{}", file.getAbsolutePath());
        } else {
            log.error("删除失败：{}", file.getAbsolutePath());
        }
    }


    @NonNull
    public static XYChart miniLineXYChart(String name, @NonNull List<Double> value) {
        XYChart chart = new XYChartBuilder().width(200).height(50).build();
        chart.getStyler().setChartBackgroundColor(Color.white);
        chart.getStyler().setPlotBorderColor(Color.white);
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setXAxisTicksVisible(false);
        chart.getStyler().setYAxisTicksVisible(false);
        chart.getStyler().setXAxisTitleVisible(false);
        chart.getStyler().setYAxisTitleVisible(false);
        chart.getStyler().setYAxisMin(value.stream().mapToDouble(Double::valueOf).min().orElseThrow());
        chart.getStyler().setYAxisMax(value.stream().mapToDouble(Double::valueOf).max().orElseThrow());
        chart.getStyler().setChartPadding(1);
        chart.getStyler().setPlotBorderVisible(false);
        chart.getStyler().setLegendVisible(false);

        XYSeries series = chart.addSeries("Series", IntStream.range(0, value.size()).mapToDouble(Double::valueOf).toArray(), value.stream().mapToDouble(Double::valueOf).toArray());
        series.setLineColor(Color.lightGray);
        series.setLineStyle(SeriesLines.SOLID);
        series.setMarkerColor(Color.lightGray);
        series.setMarker(SeriesMarkers.NONE);
        series.setLineWidth(1);
        series.setShowInLegend(false);

        return chart;
    }

    public static void init(@NonNull ChartConfig config) {
        pngPath = config.getPngPath();
        suffix = config.getSuffix();
    }

    public static void bubbleDartTheme(@NotNull BubbleChart chart) {
        chart.getStyler().setPlotBorderColor(Color.BLACK);
        chart.getStyler().setChartBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotBackgroundColor(Color.BLACK);
        chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255, 60));
        chart.getStyler().setChartFontColor(Color.GRAY);
        chart.getStyler().setAxisTickLabelsColor(Color.GRAY);
        chart.getStyler().setAnnotationTextFontColor(Color.WHITE);
    }

}
