package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.num.Num;

import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
class IndicesConfigServiceImplTest {
    @Autowired
    private IndicesConfigServiceImpl indicesConfigService;
    @Autowired
    private KNodeServiceImpl kNodeService;
    @Autowired
    private KLineMapper kLineMapper;
    @Autowired
    private StockService stockService;

    @Test
    public void cont() {
        List<String> constructors = Arrays.stream(RSIIndicator.class.getConstructors())
                .map(Objects::toString)
                .map(s -> s.substring(s.indexOf("indicators.") + 11, s.indexOf(")") + 1)
                        .replaceAll("org.ta4j.core.Indicator", "-")
                        .replaceAll("Indicator", "")
                        .replaceAll("-", "Indicator")
                ).toList();
        log.info(JSONObject.toJSONString(constructors));
    }


    @Test
    void getIndicatorById() {
        List<KLine> qqq = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());

        BarSeries barSeries = kLineMapper.toBarSeries(qqq, Duration.ofDays(1));
        Indicator<Num> adx = indicesConfigService.getIndicatorById(49955L, barSeries);
        log.info(JSONObject.toJSONString(adx.stream().map(Num::doubleValue).toList()));
    }
}