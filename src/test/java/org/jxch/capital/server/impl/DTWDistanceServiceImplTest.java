package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineFeatures;
import org.jxch.capital.server.DistanceService;
import org.jxch.capital.server.Distances;
import org.jxch.capital.server.StockService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
class DTWDistanceServiceImplTest {
    @Autowired
    private DTWDistanceServiceImpl dtwDistanceService;
    @Autowired
    private StockService stockService;

    @Test
    void distance() {
        List<? > a = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build()).stream().map(k->new KLineFeatures()).toList();
        List<? > b = stockService.history(HistoryParam.builder()
                .code("SPY")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build()).stream().map(k->new KLineFeatures()).toList();

        List<? extends KLine> aFeatures = a.stream()
                .map(k -> (KLineFeatures)k) // 假设 a 中的所有 KLine 实例实际上都是 KLineFeatures
                .collect(Collectors.toList());
        List<? extends KLine> bFeatures = b.stream()
                .map(k -> (KLineFeatures)k) // 假设 b 中的所有 KLine 实例实际上都是 KLineFeatures
                .collect(Collectors.toList());

        DistanceService<? extends KLine> distanceService = new LorentzianDistanceServiceImpl();
//        distanceService.distance(aFeatures, bFeatures);

//        double distance = AppContextHolder.getContext().getBean(KLineDistanceEnum.DTW.getDistanceService()).distance(a, b);
//        double distance = dtwDistanceService.distance(a, b);
        DistanceService<KLine> dtw = Distances.getDistanceService(AppContextHolder.getContext().getBean(DTWDistanceServiceImpl.class).getName());
//        double distance =  dtw.distance(a,b);
//        log.info("distance: {}", distance);
    }
}