package org.jxch.capital.stock.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.convert.K5MCNMapper;
import org.jxch.capital.domain.dto.K5MCNCsvDto;
import org.jxch.capital.influx.api.K5MCNInfluxApi;
import org.jxch.capital.influx.point.K5MCNInfluxPoint;
import org.jxch.capital.stock.K5MCNInfluxService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class K5MCNInfluxServiceImpl implements K5MCNInfluxService {
    private final K5MCNInfluxApi k5MCNInfluxApi;
    private final K5MCNMapper k5MCNMapper;

    @Override
    public Integer saveAll(List<K5MCNInfluxPoint> points) {
        k5MCNInfluxApi.writeAll(points);
        return points.size();
    }

    @Override
    public Integer saveAllByCsv(List<K5MCNCsvDto> csvDtoList, String code) {
        saveAll(k5MCNMapper.toK5MCNInfluxPoint(csvDtoList, code));
        return csvDtoList.size();
    }

    @Override
    public Integer saveAllByCsvFiles(List<File> csvFiles) {
        return saveAllByCsvFiles(csvFiles, file -> file.getName().split("_")[0]);
    }

    @SneakyThrows
    public Integer saveAllByCsvFile(File csvFile, String code) {
        try {
            List<K5MCNCsvDto> csvDtoList = CsvUtil.getReader().read(ResourceUtil.getUtf8Reader(csvFile.getAbsolutePath()), K5MCNCsvDto.class);
            return saveAllByCsv(csvDtoList, code);
        } catch (Exception e) {
            log.error("写入失败：{}  -[{}]", csvFile.getAbsolutePath(), e.getMessage());
            TimeUnit.SECONDS.sleep(30);
            return saveAllByCsvFile(csvFile, code);
        }
    }

    @Override
    public Integer saveAllByCsvFiles(@NotNull List<File> csvFiles, Function<File, String> codeGetter) {
        TimeInterval timer = DateUtil.timer();
        LongAdder adder = new LongAdder();
        return csvFiles.parallelStream().map(file -> {
                    int success = saveAllByCsvFile(file, codeGetter.apply(file));
                    adder.add(1);
                    log.info("[{}s. | {}m.] 进度：[{}/{}] {}条", timer.intervalSecond(), timer.intervalMinute(), adder.sum(), csvFiles.size(), success);
                    return success;
                }
        ).reduce(Integer::sum).orElse(0);
    }


}
