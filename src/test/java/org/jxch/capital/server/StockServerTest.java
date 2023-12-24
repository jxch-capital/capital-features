package org.jxch.capital.server;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class StockServerTest {
    @Autowired
    private StockServer stockServer;

    @Test
    void history() {
        var his = stockServer.history(HistoryParam.builder()
                        .code("QQQ")
                        .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3))
                .build());
        log.info(JSONObject.toJSONString(his));
    }
}