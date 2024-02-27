package org.jxch.capital.chart.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.KLineChartParam;
import org.jxch.capital.chart.dto.KLineChartRes;
import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class KLineChartServiceImplTest {
    @Autowired
    private KLineChartServiceImpl kLineChartService;

    @Test
    void chart() {
        KLineChartRes kLineChartRes = kLineChartService.chart(KLineChartParam.builder().historyParam(HistoryParam.builder().code("SPY").build()).build());


        log.info(kLineChartRes.getPath());
    }
}