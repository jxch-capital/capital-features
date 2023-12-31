package org.jxch.capital.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class KDashNode {
    private String code;
    private String name;
    private List<KLine> kLines;

    private Double d5Percent;
    private Double d20Percent;
    private Double d40Percent;
    private Double maxPrice;
    private Double minPrice;
    private List<Double> closeArr;

    @Builder
    public KDashNode(String code, String name, List<KLine> kLines) {
        this.code = code;
        this.name = name;
        this.kLines = kLines;

        if (!this.kLines.isEmpty()) {
            this.d5Percent = closePercent(5);
            this.d20Percent = closePercent(20);
            this.d40Percent = closePercent(40);
            this.maxPrice = this.kLines.stream().mapToDouble(KLine::getHigh).max().orElseThrow();
            this.minPrice = this.kLines.stream().mapToDouble(KLine::getLow).min().orElseThrow();
            this.closeArr = this.kLines.stream().map(KLine::getClose).toList();
        }
    }

    public double closePercent(int num) {
        if (kLines.size() > num) {
            return (kLines.get(kLines.size() - 1).getClose() - kLines.get(kLines.size() - num).getClose())
                    / kLines.get(kLines.size() - num).getClose() * 100;
        } else {
            return Double.NaN;
        }
    }

}
