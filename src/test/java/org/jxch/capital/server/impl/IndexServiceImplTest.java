package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.knn.distance.LorentzianKNNServiceImpl;
import org.jxch.capital.server.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class IndexServiceImplTest {
    @Autowired
    private IndexServiceImpl indexService;
    @Autowired
    private StockService stockService;
    @Autowired
    private KLineMapper kLineMapper;
    @Autowired
    private LorentzianKNNServiceImpl lorentzianKNNService;

    @Test
    void index() {
        List<KLine> kLines = stockService.history(HistoryParam.builder()
                .code("QQQ")
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -2))
                .build());

//        List<KLineIndices> kLineIndices = indexService.index(kLines, Duration.ofDays(1), new ArrayList<>(
//                Arrays.asList(
//                        (barSeries -> new CCIIndicator(barSeries, 20)),
//                        (barSeries -> new ADXIndicator(barSeries, 20)),
//                        (barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 14)),
//                        (barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 20))
//                )), 20);
//
//        List<KLineFeatures> kLineFeatures = kLineMapper.toKLineFeatures(kLineIndices);
//        lorentzianKNNService.distance()

//        log.info(JSONObject.toJSONString(kLineIndices));
    }
}