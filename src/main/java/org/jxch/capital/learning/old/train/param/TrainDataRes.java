package org.jxch.capital.learning.old.train.param;

public interface TrainDataRes {

    double[][][] getFeatures();

    int[] getSignals(SignalType signalType);

    default int[] getDefaultSignals() {
        return getSignals(SignalType.DEFAULT);
    }

}
