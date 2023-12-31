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
    private Integer successBuyNum;
    private Integer successSellNum;
    private Integer winNum;
    private Double winRate;
    private Double winBuyRate;
    private Double winSellRate;
    private int signalLimitAbs = 5;

    public KLineSignalStatistics(List<KLineSignal> kLineSignals) {
        this.kLineSignals = kLineSignals;
        this.statistics();
    }

    public KLineSignalStatistics(List<KLineSignal> kLineSignals, int signalLimitAbs) {
        this.kLineSignals = kLineSignals;
        this.signalLimitAbs = signalLimitAbs;
        this.statistics();
    }

    public void statistics() {
        this.winNum = kLineSignals.stream().filter(KLineSignal -> KLineSignal.isSuccessSignal(signalLimitAbs)).toList().size();
        this.buyNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isBuySignal(signalLimitAbs)).toList().size();
        this.sellNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isSellSignal(signalLimitAbs)).toList().size();
        this.successBuyNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isSuccessBuySignal(signalLimitAbs)).toList().size();
        this.successSellNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isSuccessSellSignal(signalLimitAbs)).toList().size();
        int hisBuyNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isHisBuySignal(signalLimitAbs)).toList().size();
        int hisSellNum = kLineSignals.stream().filter(kLineSignal -> kLineSignal.isHisSellSignal(signalLimitAbs)).toList().size();

        this.winRate = Double.valueOf(this.winNum) / (hisBuyNum + hisSellNum) * 100;
        this.winBuyRate = Double.valueOf(this.successBuyNum) / (this.buyNum) * 100;
        this.winSellRate = Double.valueOf(this.successSellNum) / (this.sellNum) * 100;
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
