package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class KNodeAnalyzeServiceImplTest {
    @Autowired
    private KNodeAnalyzeServiceImpl kNodeAnalyzeService;

    @Test
    void search() {
        List<KLine> kLines = kNodeAnalyzeService.search(340054L, "AAPL",
                DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -6),
                DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3));

        log.info(JSONObject.toJSONString(kLines));
    }
}