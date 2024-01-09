package org.jxch.capital.learning.classifier;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.learning.classifier.dto.ClassifierFitInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ClassifierConfigServiceImplTest {
    @Autowired
    private ClassifierConfigServiceImpl classifierService;

    @Test
    void getAllClassificationInfo() {
        List<ClassifierFitInfoDto> info = classifierService.getAllClassificationSupportInfo();
        log.info(JSONObject.toJSONString(info));
    }

}