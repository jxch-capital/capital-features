package org.jxch.capital.learning.train.data;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.model.dto.PredictionParam;
import org.jxch.capital.learning.train.param.TrainDataRes;

import java.util.Calendar;
import java.util.Date;

public interface TrainService {

    TrainDataRes trainData(Long trainConfigId);

    TrainDataRes predictionData(Long trainConfigId);

    TrainDataRes predictionData(Long trainConfigId, String code);

    TrainDataRes predictionData(Long trainConfigId, String code, Date start, Date end);

    default TrainDataRes predictionData(Long trainConfigId, String code, Date start) {
        return predictionData(trainConfigId, code, start, Calendar.getInstance().getTime());
    }

    default TrainDataRes predictionData(@NotNull PredictionParam param) {
        return predictionData(param.getTrainConfigId(), param.getCode(), param.getStart(), param.getEnd());
    }

}
