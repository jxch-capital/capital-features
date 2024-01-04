package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KLineSignalStatistics {
    private List<KLineSignal> kLineSignals;
    private Integer buyNum;
    private Integer sellNum;
    private Integer winNum;
    private Double winRate;

    public KLineSignalStatistics(List<KLineSignal> kLineSignals) {
        this.kLineSignals = kLineSignals;
        this.statistics();
    }

    public void statistics() {
        this.winNum = kLineSignals.stream().filter(KLineSignal -> KLineSignal.isSuccessSignal(5)).toList().size();
        this.buyNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isBuySignal(5)).toList().size();
        this.sellNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isSellSignal(5)).toList().size();
        this.winRate = Double.valueOf(this.winNum) / (this.buyNum + this.sellNum) * 100;
    }

    @Override
    public String toString() {
        return "KLineSignalStatistics{" +
                "buyNum=" + buyNum +
                ", sellNum=" + sellNum +
                ", winNum=" + winNum +
                ", winRate=" + winRate + "%" +
                '}';
    }
}
