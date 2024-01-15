package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BreathServiceImplTest {
    @Autowired
    private BreathServiceImpl breathService;

    @Test
    void getBreath() {
        BreathDto breath = breathService.getBreath(BreathParam.builder().length(90).build());
        log.info(JSONObject.toJSONString(breath.getScoresStrTable()));
    }

}