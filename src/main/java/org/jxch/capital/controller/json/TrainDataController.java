package org.jxch.capital.controller.json;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.train.param.PredictionDataRes;
import org.jxch.capital.learning.train.param.dto.PredictionParam;
import org.jxch.capital.learning.train.param.TrainDataRes;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.dto.TrainParam;
import org.jxch.capital.utils.ServiceU;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping(path = "/learning_data")
@RequiredArgsConstructor
public class TrainDataController {
    private final TrainService trainService;

    @ResponseBody
    @RequestMapping("train_data")
    public TrainDataRes trainData(@RequestBody @NonNull TrainParam param) {
        try {
            // todo 做切面
            ServiceU.setExternalService();
            TrainDataRes trainDataRes = trainService.trainData(param.getTrainConfigId());
            log.info("开始返回数据");
            return trainDataRes;
        } finally {
            ServiceU.removeExternalMark();
            log.info("end.");
        }
    }

    @ResponseBody
    @RequestMapping("prediction_data")
    public PredictionDataRes predictionData(@RequestBody @NonNull PredictionParam param) {
        try {
            ServiceU.setExternalService();
            return trainService.predictionData(param);
        } finally {
            ServiceU.removeExternalMark();
        }
    }

}
