package org.jxch.capital.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.train.data.TrainIndicesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TrainIndicesDataServiceImplTest {
    @Autowired
    private TrainIndicesDataService trainIndicesDataService;

//    @Test
//    void trainData() {
//        KNodeParam kNodeParam = KNodeParams.trainDataParam(539952L, 40, 4, 64951L);
//        KNodeTrains kNodeTrains = trainIndicesDataService.trainData(kNodeParam);
//        log.info(JSONObject.toJSONString(kNodeTrains.getFeaturesT()));
//    }
//
//    @Test
//    void predictionData() {
//        KNodeParam kNodeParam1 = KNodeParams.predictionDataParam("QQQ", Calendar.getInstance().getTime(), 40, 4, 64951L);
//        KNodeParam kNodeParam2 = KNodeParams.predictionDataParam("SPY", Calendar.getInstance().getTime(), 40, 4, 64951L);
//        KNodeTrains kNodeTrains = trainIndicesDataService.predictionData(Arrays.asList(kNodeParam1, kNodeParam2));
//        log.info(JSONObject.toJSONString(kNodeTrains.getFeaturesT()));
//    }

}