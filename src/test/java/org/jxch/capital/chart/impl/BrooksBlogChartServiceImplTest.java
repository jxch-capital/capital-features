package org.jxch.capital.chart.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.BrooksBlogChartParam;
import org.jxch.capital.chart.dto.BrooksBlogChartRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BrooksBlogChartServiceImplTest {
    @Autowired
    private BrooksBlogChartServiceImpl service;

    @Test
    void chart() {
        BrooksBlogChartRes res = service.chart(new BrooksBlogChartParam());
        log.info(JSON.toJSONString(res));
//        service.clear(res);
    }

}