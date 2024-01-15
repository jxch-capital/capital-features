package org.jxch.capital.http.logic;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BreathApiTest {
    @Autowired
    private BreathApi breathApi;

    @Test
    void get() {
        BreathDto breath = breathApi.getBreath();
        log.info(JSONObject.toJSONString(breath));
    }

}