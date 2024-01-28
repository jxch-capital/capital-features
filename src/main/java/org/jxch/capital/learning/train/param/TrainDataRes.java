package org.jxch.capital.learning.train.param;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface TrainDataRes {

    double[][][] getFeatures();

    List<KLine> getSourceKLines();

}