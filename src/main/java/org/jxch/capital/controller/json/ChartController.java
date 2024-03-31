package org.jxch.capital.controller.json;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.*;
import org.jxch.capital.chart.impl.BreathChartServiceImpl;
import org.jxch.capital.chart.impl.KLineChartServiceImpl;
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

    @SneakyThrows
    @PostMapping("kline")
    public ResponseEntity<StreamingResponseBody> kline(@RequestBody KLineChartParam param) {
        KLineChartRes kLineChartRes = kLineChartService.chart(param);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(outputStream -> {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(Paths.get(kLineChartRes.getPath()).toFile()))) {
                StreamUtils.copy(in, outputStream);
            } finally {
                kLineChartService.clear(kLineChartRes);
            }
        });
    }

    @SneakyThrows
    @PostMapping("pool")
    public ResponseEntity<StreamingResponseBody> pool(@RequestBody StockPoolBubbleChartParam param) {
        StockPoolScatterChartRes chartRes = stockPoolBubbleChartService.chart(param);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(outputStream -> {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(Paths.get(chartRes.getPath()).toFile()))) {
                StreamUtils.copy(in, outputStream);
            } finally {
                stockPoolBubbleChartService.clear(chartRes);
            }
        });
    }

    @SneakyThrows
    @PostMapping("breath")
    public ResponseEntity<StreamingResponseBody> breath(@RequestBody BreathChartParam param) {
        BreathChartRes breathChartRes = breathChartService.chart(param);

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(outputStream -> {
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(Paths.get(breathChartRes.getPath()).toFile()))) {
                StreamUtils.copy(in, outputStream);
            } finally {
                breathChartService.clear(breathChartRes);
            }
        });
    }

}
