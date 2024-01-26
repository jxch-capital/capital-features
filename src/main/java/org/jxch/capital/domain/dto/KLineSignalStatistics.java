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
    private Double signalLimitAbs = 5.0;

    public KLineSignalStatistics(List<KLineSignal> kLineSignals) {
        this.kLineSignals = kLineSignals;
        this.statistics();
    }

    public KLineSignalStatistics(List<KLineSignal> kLineSignals, int signalLimitAbs) {
        this.kLineSignals = kLineSignals;
        this.signalLimitAbs = (double) signalLimitAbs;
        this.statistics();
    }

    public KLineSignalStatistics(List<KLineSignal> kLineSignals, double signalLimitAbs) {
        this.kLineSignals = kLineSignals;
        this.signalLimitAbs = signalLimitAbs;
        this.statistics();
    }

    public KLineSignalStatistics setSignalLimitAbs(Integer signalLimitAbs) {
        this.signalLimitAbs = (double) signalLimitAbs;
        return this;
    }

    public KLineSignalStatistics setSignalLimitAbs(Double signalLimitAbs) {
        this.signalLimitAbs = signalLimitAbs;
        return this;
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

    public KLineSignalStatistics resetTureSignalByFutureSize(int futureSize) {
        kLineSignals.forEach(kLineSignal -> kLineSignal.setTureSignal(null));
        for (int i = 0; i < kLineSignals.size() - futureSize; i++) {
            kLineSignals.get(i).setTureSignal(kLineSignals.get(i + futureSize).getKLine().getClose() - kLineSignals.get(i).getKLine().getClose());
        }
        statistics();
        return this;
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
