package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KLineSignal {
    private KLine kLine;
    private Integer signal;

    @Builder.Default
    private Double tureSignal = null;

    public boolean hasTureSignal() {
        return Objects.nonNull(tureSignal);
    }

    public boolean isBuySignal() {
        return signal > 0;
    }

    public boolean isBuySignal(double limitAbs) {
        return signal > limitAbs;
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
        return signal < 0;
    }

    public boolean isSellSignal(double limitAbs) {
        return signal < -limitAbs;
    }

    public boolean isSuccessSignal() {
        return hasTureSignal() && signal * tureSignal > 0;
    }

    public boolean isSuccessBuySignal() {
        return hasTureSignal() && signal > 0 && tureSignal > 0;
    }

    public boolean isSuccessBuySignal(double limitAbs) {
        return hasTureSignal() && signal > limitAbs && tureSignal > 0;
    }

    public boolean isSuccessSignal(double limitAbs) {
        return hasTureSignal() && ((signal > limitAbs && tureSignal > 0)
                || (signal < -limitAbs && tureSignal < 0));
    }

    public boolean isSuccessSellSignal() {
        return hasTureSignal() && signal < 0 && tureSignal < 0;
    }

    public boolean isSuccessSellSignal(double limitAbs) {
        return hasTureSignal() && signal < -limitAbs && tureSignal < 0;
    }

}
