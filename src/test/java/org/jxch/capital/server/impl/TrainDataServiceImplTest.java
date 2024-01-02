package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.server.KNodeParams;
import org.jxch.capital.server.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@SpringBootTest
class TrainDataServiceImplTest {
    @Autowired
    private TrainDataService trainDataService;

    @Test
    void trainData() {
        KNodeParam kNodeParam = KNodeParams.trainDataParam(539952L, 40, 4, 64951L);
        KNodeTrains kNodeTrains = trainDataService.trainData(kNodeParam);
        log.info(JSONObject.toJSONString(kNodeTrains.getFeaturesT()));
    }

    @Test
    void predictionData() {
        KNodeParam kNodeParam1 = KNodeParams.predictionDataParam("QQQ", Calendar.getInstance().getTime(), 40, 4, 64951L);
        KNodeParam kNodeParam2 = KNodeParams.predictionDataParam("SPY", Calendar.getInstance().getTime(), 40, 4, 64951L);
        KNodeTrains kNodeTrains = trainDataService.predictionData(Arrays.asList(kNodeParam1, kNodeParam2));
        log.info(JSONObject.toJSONString(kNodeTrains.getFeaturesT()));
    }

}