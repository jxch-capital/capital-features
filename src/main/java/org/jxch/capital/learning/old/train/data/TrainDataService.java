package org.jxch.capital.learning.old.train.data;

import com.alibaba.fastjson2.JSONObject;
import org.jxch.capital.learning.old.train.param.TrainDataParam;
import org.jxch.capital.learning.old.train.param.TrainDataRes;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockRes;

public interface TrainDataService {

    TrainDataRes trainData(TrainDataParam param);

    default PredictionDataOneStockRes predictionOneStockData(PredictionDataOneStockParam param) {
        return predictionOneStockData(param, true);
    }

    PredictionDataOneStockRes predictionOneStockData(PredictionDataOneStockParam param, boolean offset);

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
