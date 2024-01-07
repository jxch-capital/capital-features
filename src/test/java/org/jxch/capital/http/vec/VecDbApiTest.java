package org.jxch.capital.http.vec;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.vec.dto.VecDbParam;
import org.jxch.capital.http.vec.dto.VecRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class VecDbApiTest {
    @Autowired
    private VecDbApi vecDbApi;

    @Test
    void search() {
        VecRes vecRes = vecDbApi.search(VecDbParam.builder().question("如何交易趋势性交易区间交易日").build());
        log.info(JSONObject.toJSONString(vecRes));
    }

}