package org.jxch.capital.stock.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StockServerAutoImplTest {
    @Autowired
    private StockServerAutoImpl auto;

    @Test
    void simpleTest() {
        HistoryParam  param1 = new HistoryParam().setCode("QQQ");
        auto.history(param1);
        HistoryParam  param2 = new HistoryParam().setCode("QQQ");
        auto.history(param2);
        HistoryParam  param3 = new HistoryParam().setCode("QQQ");
        auto.history(param3);
    }
}