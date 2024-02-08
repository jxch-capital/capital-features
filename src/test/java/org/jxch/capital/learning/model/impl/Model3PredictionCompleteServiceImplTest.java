package org.jxch.capital.learning.model.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.learning.model.dto.Model3PredictRes;
import org.jxch.capital.learning.train.param.dto.PredictionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class Model3PredictionCompleteServiceImplTest {
    @Autowired
    private Model3PredictionCompleteServiceImpl model3PredictionCompleteService;

    @Test
    void prediction() {
    }

    @Test
    void testPrediction() {
    }

    @Test
    void predictionCarry() {
        PredictionParam predictionParam = PredictionParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -5))
                .end(Calendar.getInstance().getTime())
                .build();
        Model3PredictRes model3PredictRes = model3PredictionCompleteService.predictionCarry("model_up_40_ty.zip", predictionParam);
        log.info(JSONObject.toJSONString(model3PredictRes.getSignals()));
    }

    @Test
    void testPredictionCarry() {
        PredictionParam predictionParam = PredictionParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -5))
                .end(Calendar.getInstance().getTime())
                .build();
        Model3PredictRes model3PredictRes = model3PredictionCompleteService.predictionCarry(List.of("model_up_40_ty.zip", "model_down_40_ty.zip"), predictionParam);
        log.info(JSONObject.toJSONString(model3PredictRes.getSignals()));
    }
}