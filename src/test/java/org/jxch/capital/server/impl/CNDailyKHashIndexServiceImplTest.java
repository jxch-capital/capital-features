package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.khash.BaoStockCSVKReader;
import org.jxch.capital.khash.DailyGridKHashCNDailyIndexAggDeprecated;
import org.jxch.capital.khash.DailyGridKHashKLinesAggDeprecated;
import org.jxch.capital.server.CNDailyKHashIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;

@Slf4j
@SpringBootTest
class CNDailyKHashIndexServiceImplTest {
    @Autowired
    private CNDailyKHashIndexService cnDailyKHashIndexService;
    private final LongAdder longAdder = new LongAdder();

    Integer save(File csvFile) {
        BaoStockCSVKReader baoStockCSVKReader = new BaoStockCSVKReader();
        DailyGridKHashCNDailyIndexAggDeprecated dailyGridKHashCNDailyIndexAgg = new DailyGridKHashCNDailyIndexAggDeprecated()
                .setDailyGridKHashKLinesAgg(DailyGridKHashKLinesAggDeprecated.builder().ranger(4).hashSkip(4).build());
        baoStockCSVKReader.setCsvFile(csvFile);
        dailyGridKHashCNDailyIndexAgg.setCode(csvFile.getName().split("_")[0]);

        try {
            Integer successCount = cnDailyKHashIndexService.saveByAgg(baoStockCSVKReader, dailyGridKHashCNDailyIndexAgg);
            longAdder.increment();
            return successCount;
        } catch (Exception e) {
            return save(csvFile);
        }
    }

    @Test
    @SneakyThrows
    void saveByAgg() {
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        File file = new File("G:\\app\\backup\\data\\stock_data\\csv\\5-2");
        File[] csvFiles = Objects.requireNonNull(file.listFiles());

        CountDownLatch latch = new CountDownLatch(csvFiles.length);
        TimeInterval timer = DateUtil.timer();
        for (File csvFile : csvFiles) {
            threadPool.submit(() -> {
                Integer saved = save(csvFile);
                log.info("[{}/{}] {}s. -> {} : {}", longAdder.sum(), csvFiles.length, timer.intervalSecond(), saved, csvFile.getName());
                latch.countDown();
            });
        }

        latch.await();
        log.info("success. {}s.", timer.intervalSecond());
    }

    @Test
    void findByLeftHash() {
        Pageable pageable = PageRequest.of(1, 100);
        TimeInterval timer = DateUtil.timer();
        Page<CNDailyKHashIndexDto> page = cnDailyKHashIndexService.findBySubHash(new BigDecimal("211"), 12, pageable);
        log.info("total:{} - {}ms. {}", page.getTotalPages(), timer.interval(), JSON.toJSONString(page.toList()));
    }

}