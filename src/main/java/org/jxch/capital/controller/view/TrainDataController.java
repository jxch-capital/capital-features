package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.server.TrainDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/data")
@RequiredArgsConstructor
public class TrainDataController {
    private final TrainDataService trainDataService;

    @ResponseBody
    @RequestMapping("train")
    public KNodeTrains trainData(@RequestBody KNodeParam param) {
        return trainDataService.trainData(param);
    }

    @ResponseBody
    @RequestMapping("prediction")
    public KNodeTrains predictionData(@RequestBody KNodeParam param) {
        return trainDataService.predictionData(Collections.singletonList(param));
    }

    @ResponseBody
    @RequestMapping("predictions")
    public KNodeTrains predictionData(@RequestBody List<KNodeParam> param) {
        return trainDataService.predictionData(param);
    }

}
