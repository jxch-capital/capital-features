package org.jxch.capital.learning.train.data.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.learning.train.*;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.jxch.capital.learning.train.data.TrainDataService;
import org.jxch.capital.learning.train.data.TrainService;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final TrainConfigService trainConfigService;

    @Override
    public TrainDataRes trainData(Long trainConfigId) {
        TrainConfigDto config = trainConfigService.findById(trainConfigId);
        TrainDataService service = Trains.getTrainDataService(config.getService());
        return service.trainData(service.getParam(config.getParams()));
    }

    @Override
    public TrainDataRes predictionData(Long trainConfigId) {
        TrainConfigDto config = trainConfigService.findById(trainConfigId);
        TrainDataService service = Trains.getTrainDataService(config.getService());
        return service.predictionData(service.getParam(config.getParams()));
    }

    @Override
    public TrainDataRes predictionData(Long trainConfigId, String code) {
        TrainConfigDto config = trainConfigService.findById(trainConfigId);
        TrainDataService service = Trains.getTrainDataService(config.getService());
        return service.predictionData(service.getParam(config.getParams()).setCode(code));
    }

    @Override
    public TrainDataRes predictionData(Long trainConfigId, String code, Date start, Date end) {
        TrainConfigDto config = trainConfigService.findById(trainConfigId);
        TrainDataService service = Trains.getTrainDataService(config.getService());
        TrainDataParam param = service.getParam(config.getParams());
        param.setCode(code).setStart(start).setEnd(end);
        return service.predictionData(param);
    }

}
