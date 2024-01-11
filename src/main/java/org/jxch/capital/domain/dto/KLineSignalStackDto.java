package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KLineSignalStackDto {
    private KLineSignal kLineSignal;
    private Map<String, Integer> signals = new HashMap<>();
    private Map<String, KnnSignalConfigDto> config = new HashMap<>();

    public boolean isOnlyKnnSignal() {
        return config.keySet().size() == 1;
    }

    public KnnSignalConfigDto getOnlyConfig() {
        if (isOnlyKnnSignal()) {
            return config.values().stream().toList().get(0);
        }else {
            throw new IllegalArgumentException("这不是一个单独的KNN信号");
        }
    }

    public KLineSignalStackDto(@NonNull KnnSignalConfigDto newConfig, @NonNull KLineSignal kLineSignal) {
        this.kLineSignal = kLineSignal;
        this.signals.put(newConfig.getName(), kLineSignal.getSignal());
        this.config.put(newConfig.getName(), newConfig);
    }

    public KLineSignalStackDto add(@NonNull KnnSignalConfigDto newConfig, @NonNull KLineSignalStackDto newDto) {
        signals.put(newConfig.getName(), newDto.getKLineSignal().getSignal());
        config.put(newConfig.getName(), newConfig);
        kLineSignal.setSignal(kLineSignal.getSignal() + newDto.getKLineSignal().getSignal());
        return this;
    }

    public int[] getFutureSizes(){
        return config.values().stream().mapToInt(KnnSignalConfigDto::getFutureSize).toArray();
    }

    public int getMinFutureSize() {
        return Arrays.stream(getFutureSizes()).min().orElseThrow();
    }

    public int getMaxFutureSize() {
        return Arrays.stream(getFutureSizes()).max().orElseThrow();
    }

    public double getAvgFutureSize() {
        return Arrays.stream(getFutureSizes()).average().orElseThrow();
    }

    public KLineSignalStackDto setActionSignal(int actionSignal) {
        kLineSignal.setActionSignal(actionSignal);
        return this;
    }

    public KLineSignalStackDto setTureSignal(double tureSignal) {
        kLineSignal.setTureSignal(tureSignal);
        return this;
    }

    public int getActionSignal() {
        return kLineSignal.getActionSignal();
    }

    public double getTrueSignal() {
        return kLineSignal.getTureSignal();
    }

}
