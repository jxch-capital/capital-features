package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.server.IndicesConfigService;
import org.jxch.capital.server.IntervalEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class KNodeServiceImplTest {
    @Autowired
    private KNodeServiceImpl kNodeService;
    @Autowired
    private IndicesConfigService indicesConfigService;

    @Test
    void current() {
        KNodeParam kNodeParam = KNodeParam.builder()
                .code("AAPL")
                .stockPoolId(539952)
                .maxLength(20)
                .size(1)
                .intervalEnum(IntervalEnum.DAY_1)
                .build()
                .addIndicator(IndicatorWrapper.builder().name("CCI").indicatorFunc(barSeries -> new CCIIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("ADX").indicatorFunc(barSeries -> new ADXIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-14").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 14)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-20").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 20)).build());


        KNode kNode = kNodeService.current(kNodeParam);
        log.info(JSONObject.toJSONString(kNode));
    }

    @Test
    void comparison() {
        KNodeParam kNodeParam = KNodeParam.builder()
                .code("AAPL")
                .stockPoolId(539952)
                .maxLength(20)
                .size(1)
                .intervalEnum(IntervalEnum.DAY_1)
                .build()
                .addIndicator(IndicatorWrapper.builder().name("CCI").indicatorFunc(barSeries -> new CCIIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("ADX").indicatorFunc(barSeries -> new ADXIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-14").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 14)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-20").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 20)).build());

        List<KNode> kNodes = kNodeService.comparison(kNodeParam);
        log.info(JSONObject.toJSONString(kNodes));
    }

    @Test
    void kNode() {

        KNodeParam kNodeParam = KNodeParam.builder()
                .code("AAPL")
                .stockPoolId(539952)
                .maxLength(20)
                .size(1)
                .intervalEnum(IntervalEnum.DAY_1)
                .end(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build()
                .addIndicator(IndicatorWrapper.builder().name(indicesConfigService.findById(49955L).getName())
                        .indicatorFunc(barSeries -> indicesConfigService.getIndicatorById(49955L, barSeries)).build());

        KNode kNode = kNodeService.kNode(kNodeParam);
        log.info(JSONObject.toJSONString(kNode));
    }
}