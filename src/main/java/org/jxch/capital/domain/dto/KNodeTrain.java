package org.jxch.capital.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private boolean isResetToFlat = false;

    @Builder
    public KNodeTrain(String code, @NonNull KNode kNode, int futureNum) {
        this.code = code;
        this.kLines = kNode.getKLines();
        this.futureNum = futureNum;
        this.futureIndex = this.kLines.size() - 1;
        this.futureDate = this.kLines.get(this.futureIndex).getDate();
        this.startDate = this.kLines.get(0).getDate();
        this.endIndex = this.futureIndex - this.futureNum;
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

    @JsonIgnore
    @JSONField(serialize = false)
    public void checkFutureOffset(int futureOffset) {
        if (futureOffset >= futureNum) {
            throw new IllegalArgumentException("偏移量大于预测数：" + futureOffset + " - " + futureNum);
        }
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean hasFutureUp(int futureOffset) {
        checkFutureOffset(futureOffset);
        return kLines.subList(kLines.size() - futureOffset, kLines.size()).stream()
                .anyMatch(kLine -> kLine.getHigh() > kLines.get(endIndex).getClose());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean hasFutureDown(int futureOffset) {
        checkFutureOffset(futureOffset);
        return kLines.subList(kLines.size() - futureOffset, kLines.size()).stream()
                .anyMatch(kLine -> kLine.getLow() < kLines.get(endIndex).getClose());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Double absFuturePercent() {
        return Math.abs(getFuturePercent());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public KNodeTrain resetUpToFlat() {
        up = false;
        flat = true;
        isReset = true;
        isResetToFlat = true;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public KNodeTrain resetToUp() {
        up = true;
        down =  false;
        flat = false;
        isReset = true;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public KNodeTrain resetToDown() {
        up = false;
        down =  true;
        flat = false;
        isReset = true;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public KNodeTrain resetDownToFlat() {
        down = false;
        flat = true;
        isReset = true;
        isResetToFlat = true;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public KNodeTrain resetToFlat() {
        down = false;
        up = false;
        flat = true;
        isReset = true;
        isResetToFlat = true;
        return this;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getIndices() {
        return this.kLines.subList(startIndex, endIndex + 1).stream().map(kLine -> {
            KLineIndices indices = (KLineIndices) kLine;
            return indices.getIndices().values().stream().mapToDouble(v -> v).toArray();
        }).toArray(double[][]::new);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getIndices(List<String> names) {
        return this.kLines.subList(startIndex, endIndex + 1).stream().map(kLine -> {
            KLineIndices indices = (KLineIndices) kLine;
            return indices.get(names).stream().mapToDouble(v -> v).toArray();
        }).toArray(double[][]::new);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getFeatures(List<String> names) {
        return getIndices(names);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getFeaturesT(List<String> names) {
        return getT(getIndices(names));
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getFeatures() {
        return getIndices();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    // todo 相关特征的转变应该属于KLineFeaturesDto的职责
    public double[][] getFeatures(Function<KLine, KLineFeatures> featureFunc) {
        return this.kLines.subList(startIndex, endIndex + 1).stream().map(featureFunc).map(KLineFeatures::getFeatures)
                .map(features -> features.stream().mapToDouble(v -> v).toArray())
                .toArray(double[][]::new);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    private double[][] getT(double[][] t) {
        return new SimpleMatrix(t).transpose().toArray2();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getIndicesT() {
        return getT(getIndices());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getFeaturesT() {
        return getT(getFeatures());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public double[][] getFeaturesT(Function<KLine, KLineFeatures> featureFunc) {
        return getT(getFeatures(featureFunc));
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public int upSignal() {
        return up ? 1 : 0;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public int downSignal() {
        return down ? 1 : 0;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public int flatSignal() {
        return flat ? 1 : 0;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public int signal3() {
        return up ? 1 : down ? -1 : 0;
    }

}
