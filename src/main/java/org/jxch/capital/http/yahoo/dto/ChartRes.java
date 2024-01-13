package org.jxch.capital.http.yahoo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jxch.capital.exception.StockServiceNoResException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ChartRes {
    private ChartChart chart;

    public ChartResultItem getResult() {
        return this.getChart().getResult().get(0);
    }

    public List<Long> getTimestamp() {
        return getResult().getTimestamp().stream().map(time -> time * 1000).toList();
    }

    public Integer getLength() {
        try {
            return getTimestamp().size();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public Integer getLengthThrow() {
        try {
            return getTimestamp().size();
        } catch (NullPointerException e) {
            throw new StockServiceNoResException("股票信息为空，请检查入参是否合理");
        }
    }

    public Long getTimestamp(int index) {
        return getTimestamp().get(index);
    }

    public Date getDate(int index) {
        return new Date(getTimestamp().get(index));
    }

    public ChartIndicatorsQuote getQuote() {
        return getResult().getIndicators().getQuote().get(0);
    }

    public List<Double> getClose() {
        return getQuote().getClose();
    }

    public List<Double> getOpen() {
        return getQuote().getOpen();
    }

    public List<Double> getHigh() {
        return getQuote().getHigh();
    }

    public List<Double> getLow() {
        return getQuote().getLow();
    }

    public List<Double> getVolume() {
        return getQuote().getVolume();
    }

    public Double getClose(int index) {
        return getClose().get(index);
    }

    public Double getOpen(int index) {
        return getOpen().get(index);
    }

    public Double getHigh(int index) {
        return getHigh().get(index);
    }

    public Double getLow(int index) {
        return getLow().get(index);
    }

    public Double getVolume(int index) {
        return Objects.nonNull(getVolume().get(index)) ? getVolume().get(index) : 0;
    }

    public Long getLongVolume(int index) {
        return getVolume(index).longValue();
    }

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
