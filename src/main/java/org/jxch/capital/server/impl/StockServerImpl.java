package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
public class StockServerImpl implements StockService {

    @Cacheable(value = "history", unless = "#result == null")
    public List<KLine> history(@NonNull HistoryParam param) {
        return AppContextHolder.getContext().getBean(param.getEngine().getService())
                .history(param);
    }



}
