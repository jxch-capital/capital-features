package org.jxch.capital.watch.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.watch.StockPoolWatchMailService;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolWatchMailServiceImpl implements StockPoolWatchMailService {
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;
    private final TemplateEngine templateEngine;

    @Override
    public String watchTask() {
        List<StockPoolDto> stockPoolDto = stockPoolService.findAll();

        Context context = new Context();
        context.setVariable("param1", "value1");
        context.setVariable("param2", "value2");

        return templateEngine.process("mail/stock_pool", context);
    }

}
