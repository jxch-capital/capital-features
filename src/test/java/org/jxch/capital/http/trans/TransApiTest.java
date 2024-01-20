package org.jxch.capital.http.trans;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TransApiTest {
    @Autowired
    private TransApi transApi;

    @Test
    void trans() {
        String res = transApi.trans(TransParam.builder().text("Hello").build());
        log.info(res);
    }
}