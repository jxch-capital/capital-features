package org.jxch.capital.chart.dto;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.ChartConfig;
import org.jxch.capital.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class StockPoolChartParamTest {
    @Autowired
    private StockService stockService;
    @Autowired
    private ChartConfig chartConfig;


}