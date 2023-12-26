package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import smile.math.distance.EuclideanDistance;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class DistanceServiceImplTest {
    @Autowired
    private DistanceServiceImpl distanceService;
    @Autowired
    private StockService stockService;

    @Test
    void distance() {
        List<KLine> a = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());
        List<KLine> b = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());

//        DynamicTimeWarping<double[]> timeWarping = new DynamicTimeWarping<>(new EuclideanDistance());
        double distance = distanceService.distance(a, b, EuclideanDistance::new);
        log.info("Distance: {}", distance);
    }
}