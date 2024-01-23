package org.jxch.capital.controller.json;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/data")
@RequiredArgsConstructor
public class TrainDataController {
//    private final TrainIndicesDataService trainIndicesDataService;
//
//    @ResponseBody
//    @RequestMapping("train")
//    public KNodeTrains trainData(@RequestBody KNodeParam param) {
//        return trainIndicesDataService.trainData(param);
//    }
//
//    @ResponseBody
//    @RequestMapping("prediction")
//    public KNodeTrains predictionData(@RequestBody KNodeParam param) {
//        return trainIndicesDataService.predictionData(Collections.singletonList(param));
//    }
//
//    @ResponseBody
//    @RequestMapping("predictions")
//    public KNodeTrains predictionData(@RequestBody List<KNodeParam> param) {
//        return trainIndicesDataService.predictionData(param);
//    }

}
