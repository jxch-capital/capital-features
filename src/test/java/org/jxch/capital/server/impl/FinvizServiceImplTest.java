package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.finviz.FinvizNewsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class FinvizServiceImplTest {
    @Autowired
    private FinvizServiceImpl finvizService;

    @Test
    void allNewsTitleTransToChinese() {
        List<FinvizNewsDto> finvizNewsDtos = finvizService.allNewsTitleTransToChinese();
        log.info(JSONObject.toJSONString(finvizNewsDtos));
    }


}