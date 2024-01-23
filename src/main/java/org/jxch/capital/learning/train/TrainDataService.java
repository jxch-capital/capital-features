package org.jxch.capital.learning.train;

public interface TrainDataService {

    TrainDataRes trainData(TrainDataParam param);

    TrainDataRes predictionData(TrainDataParam param);

    default String name() {
        return getClass().getSimpleName();
    }

    TrainDataParam getDefaultParam();

    TrainDataParam getParam(String json);


}
