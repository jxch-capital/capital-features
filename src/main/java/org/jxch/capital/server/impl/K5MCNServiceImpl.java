package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dao.K5MCNRepository;
import org.jxch.capital.domain.convert.K5MCNMapper;
import org.jxch.capital.domain.dto.K5MCNCsvDto;
import org.jxch.capital.domain.dto.K5MCNDto;
import org.jxch.capital.server.K5MCNService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class K5MCNServiceImpl implements K5MCNService {
    private final K5MCNRepository k5MCNRepository;
    private final K5MCNMapper k5MCNMapper;

    @Override
    public Integer saveAll(List<K5MCNDto> k5MCNDtoList) {
        return k5MCNRepository.saveAll(k5MCNMapper.toK5MCN(k5MCNDtoList)).size();
    }

    @Override
    public Integer saveAllByCsvFiles(@NotNull List<File> csvFiles, Function<File, String> codeGetter) {
        TimeInterval timer = DateUtil.timer();
        LongAdder adder = new LongAdder();
        return csvFiles.parallelStream().map(file -> {
                    int success = 0;
                    try {
                        success = saveAll(k5MCNMapper.toK5MCNDtoByCsvDto(CsvUtil.getReader()
                                .read(ResourceUtil.getUtf8Reader(file.getAbsolutePath()), K5MCNCsvDto.class), codeGetter.apply(file)));
                    } catch (Exception e) {
                        log.error("写入失败：{}", file.getAbsolutePath());
                        return 0;
                    } finally {
                        adder.add(1);
                        log.info("[{}s. | {}m.] 进度：[{}/{}] {}条", timer.intervalSecond(), timer.intervalMinute(), adder.sum(), csvFiles.size(), success);
                    }
                    return success;
                }
        ).reduce(Integer::sum).orElse(0);
    }

    @Override
    public Integer saveAllByCsvFiles(@NotNull List<File> csvFiles) {
        return saveAllByCsvFiles(csvFiles, file -> file.getName().split("_")[0]);
    }

    @Override
    public List<K5MCNDto> findByCodeAndTimeBetween(String code, Date start, Date end) {
        return k5MCNMapper.toK5MCNDto(k5MCNRepository.findAllByIdCodeAndIdTimeBetween(code, start, end));
    }

}
