package org.jxch.capital.chart.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class StockPoolBubbleChartServiceImplTest {
    @Autowired
    private StockPoolBubbleChartServiceImpl stockPoolChartPngService;

    @Test
    void chart() {
        StockPoolBubbleChartParam param = StockPoolBubbleChartParam.builder()
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3))
                .stockPoolIds(Arrays.asList(1L))
                .pl(20)
                .xl(60)
                .yl(120)
                .build();

        StockPoolScatterChartRes chart = stockPoolChartPngService.chart(param);
        log.info(JSONObject.toJSONString(chart));
//        stockPoolChartPngService.clear(chart);
    }

}