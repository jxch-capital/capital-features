package org.jxch.capital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntervalEnum {
    DAY_1("1d"),
    DAY_2("5d"),
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
