package org.jxch.capital.controller.json;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.train.TrainDataRes;
import org.jxch.capital.learning.train.TrainService;
import org.jxch.capital.learning.train.dto.TrainParam;
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
        return trainService.trainData(param.getTrainConfigId());
    }

    @ResponseBody
    @RequestMapping("prediction_data")
    public TrainDataRes predictionData(@RequestBody @NonNull TrainParam param) {
        return trainService.predictionData(param.getTrainConfigId(), param.getCode(), param.getStart(), param.getEnd());
    }

}
