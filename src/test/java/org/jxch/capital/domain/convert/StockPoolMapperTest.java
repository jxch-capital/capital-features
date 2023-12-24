package org.jxch.capital.domain.convert;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.domain.po.StockPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class StockPoolMapperTest {
    @Autowired
    private StockPoolMapper stockPoolMapper;

    @Test
    void toStockPoolDto() {
        List<StockPoolDto> stockPoolDto = stockPoolMapper.toStockPoolDto(List.of(new StockPool()));
        log.info(JSONObject.toJSONString(stockPoolDto));
    }
}