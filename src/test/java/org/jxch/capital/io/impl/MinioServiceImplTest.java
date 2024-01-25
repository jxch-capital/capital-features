package org.jxch.capital.io.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MinioServiceImplTest {
    @Autowired
    private MinioServiceImpl minioService;

    @Test
    void hasBucket() {
        boolean hasBucket = minioService.hasBucket("test");
        log.info(JSONObject.toJSONString(hasBucket));
    }

}