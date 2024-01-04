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

    public boolean isSellSignal() {
        return signal < 0;
    }

    public boolean isSellSignal(double limitAbs) {
        return signal < -limitAbs;
    }

    public boolean isSuccessSignal() {
        return signal * tureSignal > 0;
    }

    public boolean isSuccessSignal(double limitAbs) {
        return (signal > limitAbs && tureSignal > 0)
                || (signal < -limitAbs && tureSignal < 0);
    }

}
