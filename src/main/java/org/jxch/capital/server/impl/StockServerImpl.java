package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class StockServerImpl implements StockService {
    private final AppContextHolder appContextHolder;

    public List<KLine> history(@NonNull HistoryParam param) {
        return appContextHolder.getContext().getBean(param.getEngine().getService())
                .history(param);
    }

}
