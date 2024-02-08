package org.jxch.capital.learning.model.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.learning.train.data.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TensorflowModelPredictionTest {
    @Autowired
    private TensorflowModelPrediction tensorflowModelPrediction;
    @Autowired
    private TrainService trainService;

    @Test
    @SneakyThrows
    void prediction() {
//        File model = new File("D:\\jxch-capital\\capital-features-learning\\notebooks\\model\\model_up_40_ty");
//        TensorflowTFModelMetaData model3BaseMetaData = TensorflowTFModelMetaData.builder()
//                .modeltype(ModelTypeEnum.TENSORFLOW_MODEL_TF_ZIP.getName())
//                .inputname("serving_default_input_1:0")
//                .outputname("StatefulPartitionedCall:0")
//                .shapex1(5)
//                .shapex2(40)
//                .trainconfigid(7L)
//                .build();
//
//        Date now = Calendar.getInstance().getTime();
//        TrainIndicesDataRes trainDataRes = (TrainIndicesDataRes) trainService.predictionData(model3BaseMetaData.getTrainconfigid(), "SPY",
//                DateUtil.offset(now, DateField.YEAR, -2), now);
//
//        double[][][] featuresT = trainDataRes.getKNodeTrains().getFeaturesT();
//
//        StandardScaler standardScaler = Scalers.scalerByJsonFile(new File("D:\\jxch-capital\\capital-features-learning\\notebooks\\model\\model_up_40_ty_scaler_up.json"));
//
//        double[] prediction = tensorflowModelPrediction.prediction(Scalers.transform3(featuresT, standardScaler), model, model3BaseMetaData);
//        log.info(JSONObject.toJSONString(prediction));
    }


}