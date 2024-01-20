package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.finviz.FinvizApi;
import org.jxch.capital.http.finviz.FinvizNewsDto;
import org.jxch.capital.http.trans.TransApi;
import org.jxch.capital.http.trans.TransParam;
import org.jxch.capital.server.FinvizService;
import org.jxch.capital.utils.AsyncU;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinvizServiceImpl implements FinvizService {
    private final FinvizApi finvizApi;
    private final TransApi transApi;

    @SneakyThrows
    private List<FinvizNewsDto> trans(List<FinvizNewsDto> news) {
        AtomicInteger integer = new AtomicInteger(0);
        return AsyncU.newForkJoinPool(Runtime.getRuntime().availableProcessors() * 4).submit(() -> news.parallelStream().map(dto -> {
            log.debug("翻译进度：{}/{}", integer.incrementAndGet(), news.size());
            String trans = transApi.trans(TransParam.builder().text(dto.getTitle()).build());

            return dto.setTitle(trans);
        }).toList()).get();
    }

    @Override
    @Cacheable(value = "allNewsTitleTransToChinese")
    public List<FinvizNewsDto> allNewsTitleTransToChinese() {
        return trans(finvizApi.allNews());
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "newsTitleTransToChinese")
    public List<FinvizNewsDto> newsTitleTransToChinese() {
        return trans(finvizApi.news());
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "blogsTitleTransToChinese")
    public List<FinvizNewsDto> blogsTitleTransToChinese() {
        return trans(finvizApi.blogs());
    }

}
