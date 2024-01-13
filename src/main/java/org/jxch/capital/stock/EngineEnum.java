package org.jxch.capital.stock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jxch.capital.stock.impl.StockServicePoolDBImpl;
import org.jxch.capital.stock.impl.StockServiceYahooChartDataImpl;
import org.jxch.capital.stock.impl.StockServiceYahooCsvImpl;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EngineEnum {
    YAHOO("yahoo", StockServiceYahooCsvImpl.class),
    YAHOO_CHART("yahoo_chart", StockServiceYahooChartDataImpl.class),
    STOCK_POOL_DB("stock_pool_db",StockServicePoolDBImpl .class);

    private final String engine;
    private final Class<? extends StockService> service;

    public static EngineEnum pares(String engine) {
        return Arrays.stream(EngineEnum.values())
                .filter(engineEnum -> Objects.equals(engineEnum.getEngine(), engine))
                .findAny().orElseThrow();
    }

    public static EngineEnum defaultEngine() {
        return YAHOO_CHART;
    }

}
