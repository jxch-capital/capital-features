package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class DTWKNNServiceImplTest {
    @Autowired
    private DTWKNNServiceImpl dtwDistanceService;
    @Autowired
    private StockService stockService;
    @Autowired
    private KNodeService kNodeService;

    @Test
    void distance() {
        List<KLine> a = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build());
        List<KLine> b = stockService.history(HistoryParam.builder()
                .code("SPY")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());

        log.info(JSONObject.toJSONString(a));
    }

    @Test
    void search() {
        KNode kNode = kNodeService.current(KNodeParam.builder().code("AAPL").size(20).intervalEnum(IntervalEnum.DAY_1).build());
        List<KNode> kNodes = kNodeService.comparison(KNodeParam.builder().stockPoolId(539952).size(20).build());
        List<KNeighbor> neighbors = dtwDistanceService.search(kNode, kNodes, 8);
        log.info(JSONObject.toJSONString(neighbors));
    }

    @Test
    void current() {
        KNode kNode = kNodeService.current(KNodeParam.builder().code("AAPL").size(20).intervalEnum(IntervalEnum.DAY_1).build());
        log.info(JSONObject.toJSONString(kNode));
    }

    @Test
    void comparison() {
        List<KNode> kNodes = kNodeService.comparison(KNodeParam.builder().stockPoolId(539952).size(20).build());
        log.info(JSONObject.toJSONString(kNodes));
    }
}