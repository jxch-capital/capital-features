package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class IndicesCombinationServiceImplTest {
    @Autowired
    private IndicesCombinationService indicesCombinationService;
    @Autowired
    private KNodeService kNodeService;

    @Test
    void getIndicatorWrapper() {
        List<IndicatorWrapper> indicatorWrapper = indicesCombinationService.getIndicatorWrapper(64951L);

        KNodeParam kNodeParam = KNodeParam.builder()
                .code("AAPL")
                .stockPoolId(539952)
                .maxLength(20)
                .size(13)
                .intervalEnum(IntervalEnum.DAY_1)
                .end(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build().addIndicators(indicatorWrapper);

        KNode kNode = kNodeService.kNode(kNodeParam);
        List<KNode> kNodes = kNodeService.comparison(kNodeParam);

        KNodeTrain kNodeTrain = KNodeTrain.builder().kNode(kNode).code("AAPL").futureNum(4).build();
        List<KNodeTrain> kNodeTrains = kNodes.stream().map(k -> KNodeTrain.builder().code(k.getCode()).kNode(k).futureNum(4).build())
                .toList();
        KNodeTrains nodeTrains = KNodeTrains.builder().kNodes(kNodeTrains).build();


        log.info(JSONObject.toJSONString(kNodeTrain.getFeatures()));
        log.info(JSONObject.toJSONString(kNodeTrain.getFeaturesT()));
        log.info(JSONObject.toJSONString(nodeTrains));
    }

}