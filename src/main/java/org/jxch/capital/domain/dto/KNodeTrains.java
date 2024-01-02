package org.jxch.capital.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KNodeTrains {
    private List<KNodeTrain> kNodes;
    private double[][][] features;
    private double[][][] featuresT;
    private int[] upSignals;
    private int[] downSignals;
    private int[] signals3;

    @Builder
    public KNodeTrains(List<KNodeTrain> kNodes) {
        this.kNodes = kNodes;
        this.features = this.kNodes.stream().map(KNodeTrain::getFeatures).toArray(double[][][]::new);
        this.featuresT = this.kNodes.stream().map(KNodeTrain::getFeaturesT).toArray(double[][][]::new);
        this.upSignals = this.kNodes.stream().mapToInt(KNodeTrain::upSignal).toArray();
        this.downSignals = this.kNodes.stream().mapToInt(KNodeTrain::downSignal).toArray();
        this.signals3 = this.kNodes.stream().mapToInt(KNodeTrain::signal3).toArray();
    }

    public void feature(Function<KLine, KLineFeatures> featureFunc) {
        this.features = this.kNodes.stream().map(train -> train.getFeatures(featureFunc)).toArray(double[][][]::new);
        this.featuresT = this.kNodes.stream().map(train -> train.getFeaturesT(featureFunc)).toArray(double[][][]::new);
    }

}
