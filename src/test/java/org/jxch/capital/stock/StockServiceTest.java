package org.jxch.capital.stock;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Test
    void history() {
        List<KLine> kLines = stockService.history(HistoryParam.builder()
                .code("000001.SS")
                .start(DateUtil.parse("2024-04-18", "yyyy-MM-dd"))
                .interval(IntervalEnum.MINUTE_5.getInterval())
                .build());

        log.info(JSON.toJSONString(kLines.size()));
    }
}