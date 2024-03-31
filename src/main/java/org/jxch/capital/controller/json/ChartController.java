package org.jxch.capital.controller.json;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.dto.*;
import org.jxch.capital.chart.impl.BreathChartServiceImpl;
import org.jxch.capital.chart.impl.KLineChartServiceImpl;
import org.jxch.capital.chart.impl.KNNBubbleChartServiceImpl;
import org.jxch.capital.chart.impl.StockPoolBubbleChartServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping(path = "/chart_")
@RequiredArgsConstructor
public class ChartController {
    private final KLineChartServiceImpl kLineChartService;
    private final BreathChartServiceImpl breathChartService;
    private final StockPoolBubbleChartServiceImpl stockPoolBubbleChartService;
    private final KNNBubbleChartServiceImpl knnBubbleChartService;

    @SneakyThrows
    @PostMapping("kline")
    public ResponseEntity<StreamingResponseBody> kline(@RequestBody KLineChartParam param) {
        KLineChartRes chartRes = kLineChartService.chart(param);
        return commImage(kLineChartService, chartRes, chartRes.getPath());
    }

    @SneakyThrows
    @PostMapping("pool")
    public ResponseEntity<StreamingResponseBody> pool(@RequestBody StockPoolBubbleChartParam param) {
        StockPoolScatterChartRes chartRes = stockPoolBubbleChartService.chart(param);
        return commImage(stockPoolBubbleChartService, chartRes, chartRes.getPath());
    }

    @SneakyThrows
    @PostMapping("knn")
    public ResponseEntity<StreamingResponseBody> knn(@RequestBody KNNBubbleChartParam param) {
        KNNBubbleChartRes chartRes = knnBubbleChartService.chart(param);
        return commImage(knnBubbleChartService, chartRes, chartRes.getPath());
    }

    @SneakyThrows
    @PostMapping("breath")
    public ResponseEntity<StreamingResponseBody> breath(@RequestBody BreathChartParam param) {
        BreathChartRes chartRes = breathChartService.chart(param);
        return commImage(breathChartService, chartRes, chartRes.getPath());
    }

    public <T extends ChartParam,R extends ChartRes> ResponseEntity<StreamingResponseBody> commImage(ChartPngService<T,R> service, R chartRes, String path) {
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(outputStream -> {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(Paths.get(path).toFile()))) {
                StreamUtils.copy(in, outputStream);
            } finally {
                service.clear(chartRes);
            }
        });
    }

}
