package org.jxch.capital.stock.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryDBParam;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.stock.StockService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServicePoolDBImpl implements StockService {
    private final StockHistoryService stockHistoryService;
    private final KLineMapper kLineMapper;

    @Override
    public List<KLine> history(@NonNull HistoryParam param) {
        return kLineMapper.toKLineByStockHistoryDto(
                stockHistoryService.findByStockPoolIdAndStockCode(
                        ((HistoryDBParam) param).getStockPoolId(), param.getCode(), param.getStart(), param.getEnd())
        );
    }

}
