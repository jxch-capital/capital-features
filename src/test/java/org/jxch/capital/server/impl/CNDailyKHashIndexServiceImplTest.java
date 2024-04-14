package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.khash.BaoStockCSVKReader;
import org.jxch.capital.khash.DailyGridKHashCNDailyIndexAgg;
import org.jxch.capital.khash.DailyGridKHashKLinesAgg;
import org.jxch.capital.server.CNDailyKHashIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
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
        DailyGridKHashCNDailyIndexAgg dailyGridKHashCNDailyIndexAgg = new DailyGridKHashCNDailyIndexAgg()
                .setDailyGridKHashKLinesAgg(DailyGridKHashKLinesAgg.builder().ranger(4).hashSkip(4).build());
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
        TimeInterval timer = DateUtil.timer();
        List<CNDailyKHashIndexDto> byLeftHash = cnDailyKHashIndexService.findBySubHash(new BigDecimal("443334444"), 12);
        log.info("[{}] - {}ms.", byLeftHash.size(), timer.interval());
    }

}