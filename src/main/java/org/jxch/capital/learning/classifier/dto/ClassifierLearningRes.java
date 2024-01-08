package org.jxch.capital.learning.classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierLearningRes {
    @Builder.Default
    private double[][] xP = null;
    @Builder.Default
    private int[] yP = null;

    public ClassifierLearningRes setxP(double[][] xP) {
        this.xP = xP;
        this.yP = new int[this.xP.length];
        return this;
    }

    public int getSignal(int index) {
        return yP[index];
    }

}
