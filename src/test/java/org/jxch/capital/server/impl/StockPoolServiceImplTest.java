package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.StockPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StockPoolServiceImplTest {
    @Autowired
    private StockPoolService stockPoolService;

    @Test
    void findAll() {
        List<StockPoolDto> stockPoolDtos = stockPoolService.findAll();
        log.info(JSONObject.toJSONString(stockPoolDtos));
    }
}