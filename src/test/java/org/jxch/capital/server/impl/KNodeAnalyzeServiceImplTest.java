package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineAnalyzeStatistics;
import org.jxch.capital.domain.dto.KLineAnalyzedParam;
import org.jxch.capital.domain.dto.KLineAnalyzes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class KNodeAnalyzeServiceImplTest {
    @Autowired
    private KNodeAnalyzeServiceImpl kNodeAnalyzeService;

    @Test
    void search() {
        KLineAnalyzedParam analyzedParam = KLineAnalyzedParam.builder()
                .stockPoolId(340054L)
                .stockCode("AAPL")
                .startDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -6))
                .endDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3))
                .dateField(DateField.DAY_OF_YEAR)
                .extend(40)
                .futureNum(8)
                .build();

        List<KLine> kLines = kNodeAnalyzeService.search(analyzedParam);
        log.info(JSONObject.toJSONString(kLines));
    }


    @Test
    void analyze() {
        KLineAnalyzedParam analyzedParam = KLineAnalyzedParam.builder()
                .stockPoolId(340054L)
                .stockCode("AAPL")
                .startDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -6))
                .endDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3))
                .dateField(DateField.DAY_OF_YEAR)
                .extend(40)
                .futureNum(8)
                .build();

        KLineAnalyzes analyze = kNodeAnalyzeService.analyze(analyzedParam);
        log.info(JSONObject.toJSONString(analyze));
    }

    @Test
    void statistics() {
        KLineAnalyzedParam analyzedParam = KLineAnalyzedParam.builder()
                .stockPoolId(340054L)
                .stockCode("AAPL")
                .startDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -6))
                .endDate(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3))
                .dateField(DateField.DAY_OF_YEAR)
                .extend(40)
                .futureNum(8)
                .build();

        KLineAnalyzes analyze1 = kNodeAnalyzeService.analyze(analyzedParam);
        KLineAnalyzes analyze2 = kNodeAnalyzeService.analyze(analyzedParam);
        KLineAnalyzes analyze3 = kNodeAnalyzeService.analyze(analyzedParam);
        KLineAnalyzes analyze4 = kNodeAnalyzeService.analyze(analyzedParam);

        KLineAnalyzeStatistics statistics = kNodeAnalyzeService.statistics(Arrays.asList(analyze1, analyze2, analyze4, analyze3));
        log.info(JSONObject.toJSONString(statistics));
    }
}