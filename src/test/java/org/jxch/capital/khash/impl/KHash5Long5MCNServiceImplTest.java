package org.jxch.capital.khash.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.khash.DailyGridKHashKLinesAgg2HashSkips;
import org.jxch.capital.stock.IntervalEnum;
import org.jxch.capital.stock.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootTest
class KHash5Long5MCNServiceImplTest {
    @Autowired
    private KHash5Long5MCNServiceImpl kHash5Long5MCNService;
    @Autowired
    private StockService stockService;

    @Test
    void saveByBaoStockCsvFilesPath() {
        Integer succeeded = kHash5Long5MCNService.saveByBaoStockCsvFilesPath(new File("G:\\app\\backup\\data\\stock_data\\csv\\5-2").toPath());
        log.info("succeeded: {}", succeeded);
    }

    @Test
    void findByRealHash48InSortGroup() {
        List<List<KHash5Long5MCNDto>> group = kHash5Long5MCNService.findByRealHash48InSort3Group("222222211111122211121111122221111111111111221222");

        log.info("{}", group.stream().mapToInt(List::size).reduce(Integer::sum));
    }

    @Test
    void findByRealHash48() {
        TimeInterval timer = DateUtil.timer();
        List<KHash5Long5MCNDto> allDtoList = kHash5Long5MCNService.findByRealHash48("444444444444444444444444444444444444444444444444", 100);
        log.info("{}ms. {}", timer.intervalMs(), allDtoList.size());
    }

    @Test
    void findByRealHash48Group() {
        TimeInterval timer = DateUtil.timer();
        List<List<KHash5Long5MCNDto>> allDtoList = kHash5Long5MCNService.findByRealHash48Group("223333334", 100);
        log.info("{}ms. {}", timer.intervalMs(), allDtoList.stream().map(List::size).reduce(Integer::sum).orElse(0));
    }

    @Test
    void findByAgg() {
        String code = "000001.SS";
        HistoryParam param1 = new HistoryParam().setCode(code).setInterval(IntervalEnum.MINUTE_5.getInterval()).setStart(DateUtil.offsetDay(new Date(), -50));
        List<KLine> kLines = stockService.history(param1);
        DailyGridKHashKLinesAgg2HashSkips agg = DailyGridKHashKLinesAgg2HashSkips.builder().code(code).dailyKumValidate(false)
                .hisUpLength(6).hisDownLength(6)
                .setCodeFunc(dto -> dto.setCode(Integer.parseInt(code.split("\\.")[0])).setEx(code.split("\\.")[1]))
                .dayKLineProcessing(dayKLines -> {
                    if (dayKLines.size() == 48) {
                        return dayKLines;
                    }
                    if (dayKLines.size() == 49) {
                        return dayKLines.subList(1, dayKLines.size());
                    }
                    if (dayKLines.size() == 50) {
                        return dayKLines.subList(1, dayKLines.size() - 1);
                    }

                    log.warn(String.format("异常数据，一天拥有{}根K线，code:{} date:{}", dayKLines.size(), code, DateUtil.format(dayKLines.get(0).getDate(), "yyyy-MM-dd")));
                    return null;
                }).build();

        TimeInterval timer = DateUtil.timer();
        List<KHash5Long5MCNDto> byAgg2Group = kHash5Long5MCNService.findByAgg(kLines, agg, 100);
        log.info("{}ms. {}", timer.intervalMs(), byAgg2Group.size());
    }

    @Test
    void findByAgg2Group() {
    }
}