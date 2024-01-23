package org.jxch.capital.learning.train;

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

}
