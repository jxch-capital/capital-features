package org.jxch.capital.yahoo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuoteResponseResultItem {
    private String language;

    private String region;

    private String quoteType;

    private String typeDisp;

    private String quoteSourceName;

    private Boolean triggerable;

    private String customPriceAlertConfidence;

    private String currency;

    private String marketState;

    private Double regularMarketChangePercent;

    private Double regularMarketPrice;

    private String exchange;

    private String shortName;

    private String longName;

    private String messageBoardId;

    private String exchangeTimezoneName;

    private String exchangeTimezoneShortName;

    private Long gmtOffSetMilliseconds;

    private String market;

    private Boolean esgPopulated;

    private Long firstTradeDateMilliseconds;

    private Integer priceHint;

    private Double postMarketChangePercent;

    private Long postMarketTime;

    private Double postMarketPrice;

    private Double postMarketChange;

    private Double regularMarketChange;

    private Long regularMarketTime;

    private Double regularMarketDayHigh;

    private String regularMarketDayRange;

    private Double regularMarketDayLow;

    private Long regularMarketVolume;

    private Double regularMarketPreviousClose;

    private Double bid;

    private Double ask;

    private Integer bidSize;

    private Integer askSize;

    private String fullExchangeName;

    private String financialCurrency;

    private Double regularMarketOpen;

    private Long averageDailyVolume3Month;

    private Long averageDailyVolume10Day;

    private Double fiftyTwoWeekLowChange;

    private Double fiftyTwoWeekLowChangePercent;

    private String fiftyTwoWeekRange;

    private Double fiftyTwoWeekHighChange;

    private Double fiftyTwoWeekHighChangePercent;

    private Double fiftyTwoWeekLow;

    private Double fiftyTwoWeekHigh;

    private Double fiftyTwoWeekChangePercent;

    private Double trailingAnnualDividendRate;

    private Double trailingPE;

    private Double trailingAnnualDividendYield;

    private Double dividendYield;

    private Double ytdReturn;

    private Double trailingThreeMonthReturns;

    private Double trailingThreeMonthNavReturns;

    private Double netAssets;

    private Double epsTrailingTwelveMonths;

    private Long sharesOutstanding;

    private Double bookValue;

    private Double fiftyDayAverage;

    private Double fiftyDayAverageChange;

    private Double fiftyDayAverageChangePercent;

    private Double twoHundredDayAverage;

    private Double twoHundredDayAverageChange;

    private Double twoHundredDayAverageChangePercent;

    private Double netExpenseRatio;

    private Long marketCap;

    private Double priceToBook;

    private Integer sourceInterval;

    private Integer exchangeDataDelayedBy;

    private Boolean tradeable;

    private Boolean cryptoTradeable;

    private String symbol;
}
