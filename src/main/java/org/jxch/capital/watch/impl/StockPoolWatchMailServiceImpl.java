package org.jxch.capital.watch.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.jxch.capital.chart.impl.StockPoolBubbleChartServiceImpl;
import org.jxch.capital.watch.StockPoolWatchMailService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolWatchMailServiceImpl implements StockPoolWatchMailService {
    private final StockPoolBubbleChartServiceImpl stockPoolChartPngService;
    private final ThreadLocal<List<StockPoolScatterChartRes>> resThreadLocal = new ThreadLocal<>();

    @Override
    public String htmlBuild(String html) {
        return html + "<div><img src=\"cid:stock_pool_img\" /></div>";
    }

    @Override
    @SneakyThrows
    public void addInline(@NonNull MimeMessageHelper helper) {
        StockPoolBubbleChartParam param = StockPoolBubbleChartParam.builder()
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3))
                .stockPoolIds(Arrays.asList(554952L))
                .pl(20)
                .xl(60)
                .yl(120)
                .build();

        StockPoolScatterChartRes res = stockPoolChartPngService.chart(param);
        helper.addInline("stock_pool_img", new File(res.getPath()));
        resThreadLocal.set(Collections.singletonList(res));
    }

    @Override
    public void clear() {
        resThreadLocal.get().forEach(stockPoolChartPngService::clear);
        resThreadLocal.remove();
    }

    @Override
    public int getOrder() {
        return 1;
    }

}
