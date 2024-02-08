package org.jxch.capital.learning.train.data;

import com.alibaba.fastjson2.JSONObject;
import org.jxch.capital.learning.train.param.PredictionDataParam;
import org.jxch.capital.learning.train.param.PredictionDataRes;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.learning.train.param.TrainDataRes;

public interface TrainDataService {

    TrainDataRes trainData(TrainDataParam param);

    default PredictionDataRes predictionData(PredictionDataParam param) {
        return predictionData(param, true);
    }

    PredictionDataRes predictionData(PredictionDataParam param, boolean offset);

    default String name() {
        return getClass().getSimpleName();
    }

    TrainDataParam getDefaultParam();

    default TrainDataParam getParam(String json){
        return JSONObject.parseObject(json, getDefaultParam().getClass());
    }

    default <T extends TrainDataParam> T getParam(String json, Class<T> clazz){
        return JSONObject.parseObject(json, clazz);
    }

}
