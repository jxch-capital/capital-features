package org.jxch.capital.domain.dto;

import com.alibaba.fastjson2.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

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
        this.features = this.kNodes.parallelStream().map(KNodeTrain::getFeatures).toArray(double[][][]::new);
        this.featuresT = this.kNodes.parallelStream().map(KNodeTrain::getFeaturesT).toArray(double[][][]::new);
        this.upSignals = this.kNodes.parallelStream().mapToInt(KNodeTrain::upSignal).toArray();
        this.downSignals = this.kNodes.parallelStream().mapToInt(KNodeTrain::downSignal).toArray();
        this.signals3 = this.kNodes.parallelStream().mapToInt(KNodeTrain::signal3).toArray();
    }

    public KNodeTrains(@NotNull List<KNodeTrain> kNodes, List<String> featureNames, boolean simplify) {
        this.upSignals = kNodes.parallelStream().mapToInt(KNodeTrain::upSignal).toArray();
        this.downSignals = kNodes.parallelStream().mapToInt(KNodeTrain::downSignal).toArray();
        this.featuresT = kNodes.parallelStream().map(kNodeTrain -> kNodeTrain.getFeaturesT(featureNames)).toArray(double[][][]::new);

        if (!simplify) {
            this.kNodes = kNodes;
            this.signals3 = kNodes.parallelStream().mapToInt(KNodeTrain::signal3).toArray();
            this.features = kNodes.parallelStream().map(kNodeTrain -> kNodeTrain.getFeatures(featureNames)).toArray(double[][][]::new);
        }
    }

    public KNodeTrains feature(Function<KLine, KLineFeatures> featureFunc) {
        this.features = this.kNodes.parallelStream().map(train -> train.getFeatures(featureFunc)).toArray(double[][][]::new);
        this.featuresT = this.kNodes.parallelStream().map(train -> train.getFeaturesT(featureFunc)).toArray(double[][][]::new);
        return this;
    }

    public KNodeTrains feature(List<String> indicesNames) {
        return feature(kLine -> {
            KLineIndices kLineIndices = (KLineIndices) kLine;
            KLineFeatures kLineFeatures = KLineFeatures.buildKLineFeatures(kLineIndices).setFeatures(kLineIndices.get(indicesNames));
            if (kLineFeatures.isHasNull()) {
                throw new IllegalArgumentException("特征数据中存在Null: " + JSONObject.toJSONString(kLineFeatures));
            }
            return kLineFeatures;
        });
    }

}
