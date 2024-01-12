package org.jxch.capital.watch.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.StockPoolChartParam;
import org.jxch.capital.chart.impl.StockPoolScatterChartServiceImpl;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.watch.StockPoolWatchMailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolWatchMailServiceImpl implements StockPoolWatchMailService {
    private final StockPoolScatterChartServiceImpl stockPoolChartPngService;
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;
    private final TemplateEngine templateEngine;

    @Override
    public String watchTask() {
        StockPoolChartParam param = StockPoolChartParam.builder()
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .stockPoolIds(Arrays.asList(95002L, 340053L, 340054L))
                .build();

//        StockPoolChartRes res = stockPoolChartPngService.chart(param);

        Context context = new Context();
        context.setVariable("param1", "value1");
        context.setVariable("param2", "value2");

        return templateEngine.process("mail/stock_pool", context);
    }

}
