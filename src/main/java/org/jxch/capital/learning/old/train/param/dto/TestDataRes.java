package org.jxch.capital.learning.old.train.param.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.old.train.param.TestDataResForeachConsumer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TestDataRes {
    private double[][][] features;
    private double[] predictions;
    private int[] signals;

    public void foreach(TestDataResForeachConsumer consumer) {
        for (int i = 0; i < predictions.length; i++) {
            consumer.accept(features[i], predictions[i], signals[i]);
        }
    }

}
