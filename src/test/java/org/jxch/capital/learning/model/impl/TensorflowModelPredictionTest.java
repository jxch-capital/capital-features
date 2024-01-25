package org.jxch.capital.learning.model.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.learning.model.dto.Model3MetaData;
import org.jxch.capital.learning.model.dto.ModelTypeEnum;
import org.jxch.capital.learning.train.TrainService;
import org.jxch.capital.learning.train.dto.TrainIndicesDataRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@SpringBootTest
class TensorflowModelPredictionTest {
    @Autowired
    private TensorflowModelPrediction tensorflowModelPrediction;
    @Autowired
    private TrainService trainService;

    @Test
    void prediction() {
        File model = new File("");
        Model3MetaData model3MetaData = Model3MetaData.builder()
                .type(ModelTypeEnum.TENSORFLOW_MODEL.getName())
                .inputname("input_1")
                .outputname("dense_7")
                .shapex1(5)
                .shapex2(40)
                .trainconfigid(7L)
                .build();

        Date now = Calendar.getInstance().getTime();
        TrainIndicesDataRes trainDataRes = (TrainIndicesDataRes) trainService.predictionData(model3MetaData.getTrainconfigid(), "SPY",
                DateUtil.offset(now, DateField.YEAR, -2), now);

        double[][][] featuresT = trainDataRes.getKNodeTrains().getFeaturesT();
        double[][] ft = featuresT[featuresT.length - 1];


        double[] prediction = tensorflowModelPrediction.prediction(ft, model, model3MetaData);
        log.info(JSONObject.toJSONString(prediction));
    }
}