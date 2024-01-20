package org.jxch.capital.http.finviz;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class FinvizApiTest {
    @Autowired
    private FinvizApi finvizApi;

    @Test
    void news() {
        List<FinvizNewsDto> news = finvizApi.allNews();

        log.info(JSONObject.toJSONString(news));
    }
}