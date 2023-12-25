package org.jxch.capital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jxch.capital.server.impl.YahooStockServiceImpl;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum EngineEnum {
    YAHOO("yahoo", YahooStockServiceImpl.class);

    private final String engine;
    private final Class<? extends StockService> service;

    public static EngineEnum pares(String engine) {
        return Arrays.stream(EngineEnum.values())
                .filter(engineEnum -> Objects.equals(engineEnum.getEngine(), engine))
                .findAny().orElseThrow();
    }

}
