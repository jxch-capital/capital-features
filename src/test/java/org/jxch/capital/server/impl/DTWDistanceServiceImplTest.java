package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.KLineDistanceEnum;
import org.jxch.capital.server.StockService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class DTWDistanceServiceImplTest {
    @Autowired
    private DTWDistanceServiceImpl dtwDistanceService;
    @Autowired
    private StockService stockService;

    @Test
    void distance() {
        List<KLine> a = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build());
        List<KLine> b = stockService.history(HistoryParam.builder()
                .code("SPY")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());

        double distance = AppContextHolder.getContext().getBean(KLineDistanceEnum.DTW.getDistanceService()).distance(a, b);
//        double distance = dtwDistanceService.distance(a, b);
        log.info("distance: {}", distance);
    }
}