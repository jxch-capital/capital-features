package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNodeParams;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class ManhattanKNNServiceImplTest {
    @Autowired
    private ManhattanKNNServiceImpl manhattanKNNService;
    @Autowired
    private StockService stockService;
    @Autowired
    private KNodeService kNodeService;

    @Test
    void distance() {
        List<KLine> a = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());
        List<KLine> b = stockService.history(HistoryParam.builder()
                .code("SPY")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -1))
                .build());

        double distance = manhattanKNNService.distance(a, b);
        log.info("distance: {}", distance);
    }

    @Test
    void search() {
        KNodeParam kNodeParam = KNodeParams.LorentzianKNN()
                .setCode("AAPL")
                .setSize(20)
                .setIntervalEnum(IntervalEnum.DAY_1)
                .setStockPoolId(539952);

//        KNode kNode = kNodeService.current(kNodeParam);
//        List<KNode> kNodes = kNodeService.comparison(kNodeParam);
        List<KNeighbor> neighbors = manhattanKNNService.search(kNodeParam, kNodeService, 8);
        log.info(JSONObject.toJSONString(neighbors));
    }

}