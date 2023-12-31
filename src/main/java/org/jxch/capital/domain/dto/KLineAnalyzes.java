package org.jxch.capital.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KLineAnalyzes {
    private String code;
    private Date startDate;
    private Date endDate;
    private int futureNum = 4;
    private List<KLine> kLines;

    @Builder
    public KLineAnalyzes(String code, Date startDate, Date endDate, int futureNum, List<KLine> kLines) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
        this.futureNum = futureNum;
        this.kLines = kLines;
    }

    private int startIndex;
    private int endIndex;
    private int futureIndex;
    private double futurePercent;
    private int futureSignal = 0;
    private boolean startIsSet = false;
    private boolean endIsSet = false;
    private boolean hasFutureSignal = true;

    public KLineAnalyzes analyze() {
        for (int i = 0; i < this.kLines.size(); i++) {
            if (!startIsSet || !endIsSet) {
                if (!startIsSet && this.startDate.getTime() <= this.kLines.get(i).getDate().getTime()
                        && this.startDate.getTime() >= this.kLines.get(i - 1).getDate().getTime()) {
                    this.startIndex = i;
                    this.startIsSet = true;
                }
                if (!endIsSet && this.endDate.getTime() >= this.kLines.get(i).getDate().getTime()
                        && this.endDate.getTime() <= this.kLines.get(i + 1).getDate().getTime()) {
                    this.endIndex = i;
                    this.endIsSet = true;
                }
            } else {
                break;
            }
        }

        try {
            this.futureIndex = this.endIndex + this.futureNum;
            this.futurePercent = (this.kLines.get(futureIndex).getClose() - this.kLines.get(this.endIndex).getClose())
                    / Math.abs(this.kLines.get(this.endIndex).getClose() - this.kLines.get(startIndex).getClose());
            this.futureSignal = this.futurePercent > 0 ? 1 : this.futurePercent < 0 ? -1 : 0;

            return this;
        } catch (IndexOutOfBoundsException exception) {
            hasFutureSignal = false;
            return this;
        }
    }

}
