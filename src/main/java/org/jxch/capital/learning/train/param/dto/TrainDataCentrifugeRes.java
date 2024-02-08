package org.jxch.capital.learning.train.param.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataRes;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TrainDataCentrifugeRes implements TrainDataRes {
    private int[] signals;


    @Override
    public double[][][] getFeatures() {
        return new double[0][][];
    }

    @Override
    public int[] getSignals(SignalType signalType) {
        return new int[0];
    }
}
