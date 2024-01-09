package org.jxch.capital.learning.signal;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class ClassifierSignalBackTestServiceImplTest {
    @Autowired
    private ClassifierSignalBackTestServiceImpl classifierSignalBackTestService;


    @Test
    void testBackTestByCode() {
        SignalBackTestClassifierParam param = new SignalBackTestClassifierParam(119951L);

        param.setCode("QQQ").setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -2));

        List<KLineSignal> kLineSignals = classifierSignalBackTestService.backTestByCode(param);

        log.info(JSONObject.toJSONString(kLineSignals));
    }

}