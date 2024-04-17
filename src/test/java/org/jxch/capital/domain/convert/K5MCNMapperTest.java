package org.jxch.capital.domain.convert;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.K5MCNDto;
import org.jxch.capital.domain.po.K5MCN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class K5MCNMapperTest {
    @Autowired
    private K5MCNMapper k5mcnMapper;

    @Test
    void toK5MCN() {
        K5MCNDto dto = new K5MCNDto();
        dto.setHigh(345.9).setCode("test");
        K5MCN k5MCN = k5mcnMapper.toK5MCN(dto);
        log.info(JSON.toJSONString(k5MCN));
    }
}