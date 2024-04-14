package org.jxch.capital.khash;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLine;

import java.io.File;
import java.util.List;

@Slf4j
class GridNumKHashTest {

    @Test
    void hash() {
        BaoStockCSVKReader baoStockCSVKReader = new BaoStockCSVKReader();
        List<KLine> kLines = baoStockCSVKReader.setCsvFile(new File("G:\\app\\backup\\data\\stock_data\\csv\\5-2\\sz.301017_19900101-20231231.csv")).read();

        DailyGridKHashKLinesAgg dailyGridKHashKLinesAgg = DailyGridKHashKLinesAgg.builder().ranger(4).hashSkip(10).build();
        DailyGridKHashCNDailyIndexAgg dailyGridKHashCNDailyIndexAgg = new DailyGridKHashCNDailyIndexAgg()
                .setCode("sz.301017").setDailyGridKHashKLinesAgg(dailyGridKHashKLinesAgg);

        var aggregate = dailyGridKHashCNDailyIndexAgg.aggregate(kLines);
        log.info(JSON.toJSONString(aggregate));
    }

}