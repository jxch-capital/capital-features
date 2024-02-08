package org.jxch.capital.learning.train.param;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

/**
 * 继承 TrainDataRes 接口是因为预测集数据当然也需要训练集的数据支持，同时增加新的数据支持
 * 预测集是按照每只股票进行分片的，所以需要支持获取源K线数据
 */
public interface PredictionDataOneStockRes extends TrainDataRes {

    List<KLine> getSourceKLines();

}
