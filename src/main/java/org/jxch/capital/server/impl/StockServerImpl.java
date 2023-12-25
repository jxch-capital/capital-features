package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.FeaturesApp;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServerImpl implements StockService {

    public List<KLine> history(@NonNull HistoryParam param) {
        return FeaturesApp.getContext().getBean(param.getEngine().getService())
                .history(param);
    }

}
