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

    @Override
    @SneakyThrows
    @Cacheable(value = "allNewsTitleTransToChinese")
    public List<FinvizNewsDto> allNewsTitleTransToChinese() {
        List<FinvizNewsDto> allNews = finvizApi.allNews();
        AtomicInteger integer = new AtomicInteger(0);
        return AsyncU.newForkJoinPool(Runtime.getRuntime().availableProcessors() * 4).submit(() -> allNews.parallelStream().map(dto -> {
            log.debug("翻译进度：{}/{}", integer.incrementAndGet(), allNews.size());
            return dto.setTitle(transApi.trans(TransParam.builder().text(dto.getTitle()).build()));
        }).toList()).get();
    }

}
