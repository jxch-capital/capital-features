package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Objects;
import java.util.function.Function;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KLineSignal {
    private KLine kLine;
    private Integer signal;

    @Builder.Default
    private Integer actionSignal = 0;
    @Builder.Default
    private Double tureSignal = null;

    public KLineSignal actionSignal() {
        if (signal > 0 && kLine.getClose() > kLine.getOpen()) {
            actionSignal = 1;
        } else if (signal < 0 && kLine.getClose() < kLine.getOpen()) {
            actionSignal = -1;
        }
        return this;
    }

    public KLineSignal actionSignal(double limitAbs) {
        if (signal > limitAbs && kLine.getClose() > kLine.getOpen()) {
            actionSignal = 1;
        } else if (signal < -limitAbs && kLine.getClose() < kLine.getOpen()) {
            actionSignal = -1;
        }
        return this;
    }

    public KLineSignal actionSignal(@NonNull Function<KLineSignal, Integer> signalFunc) {
        actionSignal = signalFunc.apply(this);
        return this;
    }

    public boolean isActionSignal() {
        return !Objects.equals(actionSignal, 0);
    }

    public boolean hasTureSignal() {
        return Objects.nonNull(tureSignal);
    }

    public boolean isBuySignal() {
        return signal > 0 && isActionSignal();
    }

    public boolean isBuySignal(double limitAbs) {
        return signal > limitAbs && isActionSignal();
    }

    public boolean isHisBuySignal() {
        return isBuySignal() && hasTureSignal();
    }

    public boolean isHisSellSignal() {
        return isSellSignal() && hasTureSignal();
    }

    public boolean isHisSellSignal(double limitAbs) {
        return isSellSignal(limitAbs) && hasTureSignal();
    }

    public boolean isHisBuySignal(double limitAbs) {
        return isBuySignal(limitAbs) && hasTureSignal();
    }

    public boolean isSellSignal() {
        return signal < 0 && isActionSignal();
    }

    public boolean isSellSignal(double limitAbs) {
        return signal < -limitAbs && isActionSignal();
    }

    public boolean isSuccessSignal() {
        return hasTureSignal() && signal * tureSignal > 0 && isActionSignal();
    }

    public boolean isSuccessBuySignal() {
        return hasTureSignal() && signal > 0 && tureSignal > 0 && isActionSignal();
    }

    public boolean isSuccessBuySignal(double limitAbs) {
        return hasTureSignal() && signal > limitAbs && tureSignal > 0 && isActionSignal();
    }

    public boolean isSuccessSignal(double limitAbs) {
        return hasTureSignal() && ((signal > limitAbs && tureSignal > 0)
                || (signal < -limitAbs && tureSignal < 0)) && isActionSignal();
    }

    public boolean isSuccessSellSignal() {
        return hasTureSignal() && signal < 0 && tureSignal < 0 && isActionSignal();
    }

    public boolean isSuccessSellSignal(double limitAbs) {
        return hasTureSignal() && signal < -limitAbs && tureSignal < 0 && isActionSignal();
    }

}
