package org.jxch.capital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntervalEnum {
    MINUTE_1("1m"),
    MINUTE_2("2m"),
    MINUTE_5("5m"),
    MINUTE_15("15m"),
    MINUTE_30("30m"),
    MINUTE_60("60m"),
    MINUTE_90("90m"),
    HOUR_1("1h"),
    HOUR_2("2h"),
    HOUR_4("4h"),
    DAY_1("1d"),
    DAY_2("5d"),
    WEEK_1("1wk"),
    MONTH_1("1mo"),
    MONTH_3("3mo"),
    MONTH_6("6mo"),
    YEAR_1("1y"),
    YEAR_2("2y"),
    YEAR_5("5y"),
    YEAR_10("10y"),
    ;
    private final String interval;

}
