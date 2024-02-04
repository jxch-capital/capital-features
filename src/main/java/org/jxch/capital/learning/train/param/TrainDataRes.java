package org.jxch.capital.learning.train.param;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface TrainDataRes {

    double[][][] getFeatures();

    int[] getUpSignals();

    int[] getDownSignals();

    int[] get3Signals();

    List<KLine> getSourceKLines();

    int[] getSignals(SignalType signalType);

}
