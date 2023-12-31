package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.StockBaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StockBaseServiceImplTest {
    @Autowired
    private StockBaseServiceImpl stockBaseService;

    @Test
    void findAll() {
    }

    @Test
    void findByCode() {
    }

    @Test
    void save() {
        StockBaseDto stockBaseDto = StockBaseDto.builder()
                .code("QQQ")
                .build();

        stockBaseService.save(Collections.singletonList(stockBaseDto));
        List<StockBaseDto> stockBaseServiceAll = stockBaseService.findAll();
        log.info(JSONObject.toJSONString(stockBaseServiceAll));
    }

    @Test
    void del() {
        stockBaseService.del(Collections.singletonList(-49L));
        List<StockBaseDto> stockBaseServiceAll = stockBaseService.findAll();
        log.info(JSONObject.toJSONString(stockBaseServiceAll));
    }
}