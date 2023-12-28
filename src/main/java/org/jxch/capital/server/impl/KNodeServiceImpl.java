package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.StockHistoryDto;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.StockHistoryService;
import org.jxch.capital.server.StockService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class KNodeServiceImpl implements KNodeService {
    private final StockHistoryService stockHistoryService;
    private final StockService stockService;
    private final KLineMapper kLineMapper;
    @Setter
    private static int offsetMultiples = 5;

    @Override
    @Cacheable(value = "currentCache", key = "#code + '_' + #size + '_' + #intervalEnum.name()", unless = "#result == null")
    public KNode current(String code, int size, @NonNull IntervalEnum intervalEnum) {
        List<KLine> history = stockService.history(HistoryParam.builder()
                        .code(code)
                        .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.DAY_OF_YEAR, -size * offsetMultiples))
                        .interval(intervalEnum.getInterval())
                        .build())
                .stream()
                .sorted(Comparator.comparing(KLine::getDate).reversed())
                .limit(size)
                .sorted(Comparator.comparing(KLine::getDate))
                .toList();

        return KNode.builder()
                .code(code)
                .kLines(history)
                .build();
    }

    @Override
    @Cacheable(value = "comparisonCache", key = "#stockPoolId + '_' + #size", unless = "#result.isEmpty()")
    public List<KNode> comparison(long stockPoolId, int size) {
        return stockHistoryService.findByStockPoolId(stockPoolId).stream()
                .collect(Collectors.groupingBy(StockHistoryDto::getStockCode))
                .entrySet().stream().flatMap(entry ->
                        IntStream.range(0, entry.getValue().size() - size + 1)
                                .mapToObj(start -> entry.getValue().subList(start, start + size))
                                .map(kLines -> KNode.builder().code(entry.getKey()).kLines(kLineMapper.toKLineByStockHistoryDto(kLines)).build())
                ).toList();
    }

}
