package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.knn.KNNAutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class KNNAutoServiceImplTest {
    @Autowired
    private KNNAutoServiceImpl knnAutoService;

    @Test
    void search() {
        KNodeParam kNodeParam = KNodeParam.builder()
                .code("QQQ")
                .stockPoolId(539952)
                .build();

        List<KNeighbor> neighbors = knnAutoService.search("DTW-结构优先", kNodeParam, 20);
        log.info(JSONObject.toJSONString(neighbors));
    }
}