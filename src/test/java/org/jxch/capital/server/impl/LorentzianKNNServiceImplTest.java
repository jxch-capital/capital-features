package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.knn.distance.LorentzianKNNServiceImpl;
import org.jxch.capital.server.KNodeParams;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class LorentzianKNNServiceImplTest {
    @Autowired
    private LorentzianKNNServiceImpl lorentzianKNNService;
    @Autowired
    private StockService stockService;
    @Autowired
    private KNodeService kNodeService;

    @Test
    void features() {
        List<KLine> kLines = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build());

//        List<KLineFeatures> features = lorentzianKNNService.features(kLines);
//        log.info(JSONObject.toJSONString(features));
    }

    @Test
    void distance() {

    }

    @Test
    void search() {
        KNodeParam kNodeParam = KNodeParams.LorentzianKNN()
                .setCode("QQQ")
                .setStockPoolId(539952);

        List<KNeighbor> neighbors = lorentzianKNNService.search(kNodeParam, kNodeService, 20);
        log.info(JSONObject.toJSONString(neighbors));
    }
}