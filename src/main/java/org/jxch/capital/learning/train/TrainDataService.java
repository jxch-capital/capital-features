package org.jxch.capital.learning.train;

public interface TrainDataService {

    TrainDataRes trainData(TrainDataParam param);

    default TrainDataRes predictionData(TrainDataParam param) {
        return predictionData(param, true);
    }

    TrainDataRes predictionData(TrainDataParam param, boolean offset);

    default String name() {
        return getClass().getSimpleName();
    }

    TrainDataParam getDefaultParam();

    TrainDataParam getParam(String json);


}
