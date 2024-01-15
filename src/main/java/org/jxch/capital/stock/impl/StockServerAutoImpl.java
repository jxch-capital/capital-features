package org.jxch.capital.stock.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class StockServerAutoImpl implements StockService {

    @Override
    public List<KLine> history(@NonNull HistoryParam param) {
        log.debug("查询：{}", JSONObject.toJSONString(param));
        return AppContextHolder.getContext().getBean(param.getEngine().getService())
                .history(param);
    }

    @Override
    @Cacheable(value = "history", keyGenerator = "HistoryParam1dKeyGenerator", unless = "#result == null")
    public List<KLine> history1d(@NonNull HistoryParam param) {
        return history(param);
    }

}
