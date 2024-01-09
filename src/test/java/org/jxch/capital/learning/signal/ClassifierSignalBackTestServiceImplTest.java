package org.jxch.capital.learning.signal;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.jxch.capital.server.IntervalEnum;
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
    void backTestByCode() {
        SignalBackTestClassifierParam param = new SignalBackTestClassifierParam()
                .setClassifierName("SVM+GaussianKernel-H")
                .setKNodeParam(KNodeParam.builder()
                        .stockPoolId(539952)
                        .maxLength(20)
                        .size(20)
                        .intervalEnum(IntervalEnum.DAY_1)
                        .build())
                ;
        param.setFutureNum(6).setCode("QQQ").setSignalLimitAbs(0).setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -2));

        List<KLineSignal> kLineSignals = classifierSignalBackTestService.backTestByCode(param);
        log.info(JSONObject.toJSONString(kLineSignals));
    }
}