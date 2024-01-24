package org.jxch.capital.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.ejml.simple.SimpleMatrix;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KNodeTrain {
    private String code;
    private List<KLine> kLines;
    private int futureNum = 4;

    private int startIndex = 0;
    private int endIndex;
    private int futureIndex;
    private Date startDate;
    private Date endDate;
    private Date futureDate;

    private boolean up = false;
    private boolean down = false;
    private boolean flat = false;
    private double futurePercent;
    private boolean isReset = false;

    @Builder
    public KNodeTrain(String code, @NonNull KNode kNode, int futureNum) {
        this.code = code;
        this.kLines = kNode.getKLines();
        this.futureNum = futureNum;
        this.futureIndex = this.kLines.size() - 1;
        this.futureDate = this.kLines.get(this.futureIndex).getDate();
        this.startDate = this.kLines.get(0).getDate();
        this.endIndex = this.kLines.size() - 1 - this.futureNum;
        this.endDate = this.kLines.get(this.endIndex).getDate();

        if (this.kLines.get(this.futureIndex).getLow() > this.kLines.get(this.endIndex).getHigh()) {
            up = true;
        } else if (this.kLines.get(this.futureIndex).getHigh() < this.kLines.get(this.endIndex).getLow()) {
            down = true;
        } else {
            flat = true;
        }

        this.futurePercent = (this.kLines.get(futureIndex).getClose() - this.kLines.get(this.endIndex).getClose())
                / this.kLines.get(this.endIndex).getClose() * 100;
    }

    public KNodeTrain resetUpToFlat() {
        up = false;
        flat = true;
        isReset = true;
        return this;
    }

    public KNodeTrain resetDownToFlat() {
        down = false;
        flat = true;
        isReset = true;
        return this;
    }

    public double[][] getIndices() {
        return this.kLines.subList(startIndex, endIndex).stream().map(kLine -> {
            KLineIndices indices = (KLineIndices) kLine;
            return indices.getIndices().values().stream().mapToDouble(v -> v).toArray();
        }).toArray(double[][]::new);
    }

    public double[][] getFeatures() {
        return getIndices();
    }

    // todo 相关特征的转变应该属于KLineFeaturesDto的职责
    public double[][] getFeatures(Function<KLine, KLineFeatures> featureFunc) {
        return this.kLines.subList(startIndex, endIndex).stream().map(featureFunc).map(KLineFeatures::getFeatures)
                .map(features -> features.stream().mapToDouble(v -> v).toArray())
                .toArray(double[][]::new);
    }

    private double[][] getT(double[][] t) {
        return new SimpleMatrix(t).transpose().toArray2();
    }

    public double[][] getIndicesT() {
        return getT(getIndices());
    }

    public double[][] getFeaturesT() {
        return getT(getFeatures());
    }

    public double[][] getFeaturesT(Function<KLine, KLineFeatures> featureFunc) {
        return getT(getFeatures(featureFunc));
    }

    public int upSignal() {
        return up ? 1 : 0;
    }

    public int downSignal() {
        return down ? 1 : 0;
    }

    public int flatSignal() {
        return flat ? 1 : 0;
    }

    public int signal3() {
        return up ? 1 : down ? -1 : 0;
    }

}
