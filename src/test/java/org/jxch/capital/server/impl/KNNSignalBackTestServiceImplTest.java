package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KLineSignalStatistics;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNNSignalBackTestParam;
import org.jxch.capital.knn.distance.LorentzianKNNServiceImpl;
import org.jxch.capital.knn.signal.KNNSignalBackTestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class KNNSignalBackTestServiceImplTest {
    @Autowired
    private KNNSignalBackTestServiceImpl knnSignalBackTestService;
    @Autowired
    private LorentzianKNNServiceImpl lorentzianKNNService;

    @Test
    void backTestByCode() {
        KNNSignalBackTestParam param = new KNNSignalBackTestParam();
        param.setKnnParam(KNNParam.builder()
                        .neighborSize(20)
                        .distanceName(lorentzianKNNService.getName())
                        .kNodeParam(lorentzianKNNService.getDefaultKNodeParam()
                                .setStockPoolId(340054)
                                .setIndicesComId(69951))
                        .build())
                .setFutureNum(2)
                .setCode("QQQ")
                .setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -4));

        List<KLineSignal> kLineSignals = knnSignalBackTestService.backTestByCode(param);
        KLineSignalStatistics statistics = new KLineSignalStatistics(kLineSignals);
        log.info(JSONObject.toJSONString(statistics.toString()));
    }

}