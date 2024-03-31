package org.jxch.capital.chart.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.BreathChartParam;
import org.jxch.capital.chart.dto.BreathChartRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BreathChartServiceImplTest {
    @Autowired
    private BreathChartServiceImpl breathChartService;

    @Test
    void chart() {
        BreathChartRes chartRes = breathChartService.chart(BreathChartParam.builder().build());
        log.info(JSON.toJSONString(chartRes));
        breathChartService.clear(chartRes);
    }
}