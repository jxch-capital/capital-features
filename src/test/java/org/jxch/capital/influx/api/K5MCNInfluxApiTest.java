package org.jxch.capital.influx.api;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.influx.point.K5MCNInfluxPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class K5MCNInfluxApiTest {
    @Autowired
    private K5MCNInfluxApi k5MCNInfluxApi;

    @Test
    public void testApi() {
        K5MCNInfluxPoint k5MCNInfluxPoint = K5MCNInfluxPoint.builder()
                .code("test")
                .time(DateUtil.parse("2024-04-01 08:12:12", "yyyy-MM-dd HH:mm:ss"))
                .open(2.0)
                .close(3.0)
                .high(4.0)
                .low(2.0)
                .volume(200L)
                .amount(300L)
                .build();

        k5MCNInfluxApi.write(k5MCNInfluxPoint);

        List<K5MCNInfluxPoint> k5MCNInfluxPoints = k5MCNInfluxApi.queryByExampleTagEntityAndTimeBetween(K5MCNInfluxPoint.builder().volume(200L).build(),
                DateUtil.parse("2024-04-01 1:12:12", "yyyy-MM-dd HH:mm:ss"),
                DateUtil.parse("2024-04-02 12:12:12", "yyyy-MM-dd HH:mm:ss"), K5MCNInfluxPoint.class);

        log.info(JSON.toJSONString(k5MCNInfluxPoints));
    }

    @Test
    void deletePointsByTimeBetween() {
        k5MCNInfluxApi.deletePointsByTimeBetween(DateUtil.parse("2024-04-01 1:12:12", "yyyy-MM-dd HH:mm:ss"), DateUtil.parse("2024-04-02 13:12:12", "yyyy-MM-dd HH:mm:ss"), K5MCNInfluxPoint.class);
    }

    @Test
    void query() {
        List<K5MCNInfluxPoint> k5mCn = k5MCNInfluxApi.queryTest("capital-features", DateUtil.parse("2024-03-01 10:12:12", "yyyy-MM-dd HH:mm:ss").getTime(),
                DateUtil.parse("2024-05-01 01:12:12", "yyyy-MM-dd HH:mm:ss").getTime(), "k_5m_cn");

        log.info(JSON.toJSONString(k5mCn));
    }


}