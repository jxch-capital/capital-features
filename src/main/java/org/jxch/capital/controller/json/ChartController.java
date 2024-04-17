package org.jxch.capital.controller.json;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartPngService;
import org.jxch.capital.chart.dto.*;
import org.jxch.capital.chart.impl.*;
import org.jxch.capital.exception.StockServiceNoResException;
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
import java.util.Date;

@Slf4j
@RestController
@RequestMapping(path = "/chart_")
@RequiredArgsConstructor
public class ChartController {
    private final KLineChartServiceImpl kLineChartService;
    private final BreathChartServiceImpl breathChartService;
    private final StockPoolBubbleChartServiceImpl stockPoolBubbleChartService;
    private final KNNBubbleChartServiceImpl knnBubbleChartService;
    private final BrooksBlogChartServiceImpl brooksBlogChartService;

    @SneakyThrows
    @PostMapping("kline")
    public ResponseEntity<StreamingResponseBody> kline(@RequestBody KLineChartParam param) {
        KLineChartRes chartRes = kLineChartService.chart(param);
        return commImage(kLineChartService, chartRes, chartRes.getPath());
    }

    @SneakyThrows
    @PostMapping("kline_last_daily")
    public ResponseEntity<StreamingResponseBody> klineLastDaily(@RequestBody KLineChartParam param) {
        try {
            KLineChartRes chartRes = kLineChartService.chart(param);
            return commImage(kLineChartService, chartRes, chartRes.getPath());
        } catch (StockServiceNoResException e) {
            Date start = param.getHistoryParam().getStart();
            param.getHistoryParam().setStart(DateUtil.offsetDay(start, -1));
            return klineLastDaily(param);
        }
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

    @SneakyThrows
    @PostMapping("brooks")
    public ResponseEntity<StreamingResponseBody> brooks(@RequestBody BrooksBlogChartParam param) {
        BrooksBlogChartRes chartRes = brooksBlogChartService.chart(param);
        return commImage(brooksBlogChartService, chartRes, chartRes.getPath());
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
