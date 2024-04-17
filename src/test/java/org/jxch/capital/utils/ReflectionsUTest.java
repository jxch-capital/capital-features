package org.jxch.capital.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.FeaturesApp;
import org.jxch.capital.influx.InfluxApi;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
//@SpringBootTest
class ReflectionsUTest {

    @Test
    void findClass() {
        Map<String, Class<?>> collect = ReflectionsU.scanAllClassByClassPath(FeaturesApp.class.getPackageName(), InfluxApi.class::isAssignableFrom).stream()
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
        log.info(JSON.toJSONString(collect));
    }

}