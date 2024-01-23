package org.jxch.capital.chart.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.chart.dto.KNNBubbleChartParam;
import org.jxch.capital.chart.dto.KNNBubbleChartRes;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class KNNBubbleChartServiceImplTest {
    @Autowired
    private KNNBubbleChartServiceImpl knnBubbleChartService;

    @Test
    void chart() {
        KNNBubbleChartParam param = KNNBubbleChartParam.builder()
                .knnParam(KNNParam.builder()
                        .distanceName("切比雪夫距离-指标序列-平均")
                        .neighborSize(20)
                        .kNodeParam(KNodeParam.builder()
                                .futureNum(8)
                                .code("SPY")
                                .size(20)
                                .maxLength(70)
                                .indicesComId(21L)
                                .stockPoolId(24)
                                .normalized(true)
                                .build())
                        .build())
                .build();

        KNNBubbleChartRes res = knnBubbleChartService.chart(param);
//        knnBubbleChartService.clear(res);
        log.info(JSONObject.toJSONString(res));
    }
}