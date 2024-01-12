package org.jxch.capital.chart.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.StockPoolChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@SpringBootTest
@RequiredArgsConstructor
class StockPoolScatterChartServiceImplTest {
    @Autowired
    private StockPoolScatterChartServiceImpl stockPoolChartPngService;

    @Test
    void chart() {
        StockPoolScatterChartParam param = StockPoolScatterChartParam.builder()
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -6))
                .stockPoolIds(Arrays.asList(95002L,
                        340053L,
                        340054L
                ))
                .build();

        StockPoolScatterChartRes chart = stockPoolChartPngService.chart(param);
        log.info(JSONObject.toJSONString(chart));
    }

}