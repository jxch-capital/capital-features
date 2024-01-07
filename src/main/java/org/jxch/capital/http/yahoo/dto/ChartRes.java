package org.jxch.capital.http.yahoo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ChartRes {
    private ChartChart chart;

    @Data
    @NoArgsConstructor
    public static class ChartChart {
        private List<ChartResultItem> result;
        private String error;
    }

    @Data
    @NoArgsConstructor
    public static class ChartResultItem {
        private ChartMeta meta;
        private List<Long> timestamp;
        private ChartIndicators indicators;
    }

    @Data
    @NoArgsConstructor
    public static class ChartMeta {
        private String currency;
        private String symbol;
        private String exchangeName;
        private String instrumentType;
        private Long firstTradeDate;
        private Long regularMarketTime;
        private Long gmtoffset;
        private String timezone;
        private String exchangeTimezoneName;
        private Double regularMarketPrice;
        private Double chartPreviousClose;
        private Integer priceHint;
        private CurrentTradingPeriod currentTradingPeriod;
        private String dataGranularity;
        private String range;
        private List<String> validRanges;
    }

    @Data
    @NoArgsConstructor
    public static class CurrentTradingPeriod {
        private CurrentTradingPeriodItem pre;
        private CurrentTradingPeriodItem regular;
        private CurrentTradingPeriodItem post;
    }

    @Data
    @NoArgsConstructor
    public static class CurrentTradingPeriodItem {
        private String timezone;
        private Long start;
        private Long end;
        private Long gmtoffset;
    }

    @Data
    @NoArgsConstructor
    public static class ChartIndicators {
        private List<ChartIndicatorsQuote> quote;
        private List<ChartIndicatorsQuoteAdjClose> adjclose;
    }

    @Data
    @NoArgsConstructor
    public static class ChartIndicatorsQuote {
        private List<Double> high;
        private List<Double> close;
        private List<Double> volume;
        private List<Double> open;
        private List<Double> low;
    }

    @Data
    @NoArgsConstructor
    public static class ChartIndicatorsQuoteAdjClose {
        private List<Double> adjclose;
    }

}
