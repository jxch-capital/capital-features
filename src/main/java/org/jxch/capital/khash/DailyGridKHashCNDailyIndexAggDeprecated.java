package org.jxch.capital.khash;

import cn.hutool.core.date.DateUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.domain.dto.KLine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Accessors(chain = true)
public class DailyGridKHashCNDailyIndexAggDeprecated implements KHashAggDeprecated<CNDailyKHashIndexDto> {
    private DailyGridKHashKLinesAggDeprecated dailyGridKHashKLinesAgg;
    private String code;

    @Override
    public Map<String, List<CNDailyKHashIndexDto>> aggregate(List<KLine> kLines) {
        Map<String, List<List<KLine>>> aggregate = dailyGridKHashKLinesAgg.aggregate(kLines);
        Map<String, List<CNDailyKHashIndexDto>> result = new HashMap<>();
        for (String key : aggregate.keySet()) {
            List<CNDailyKHashIndexDto> kHashIndexDtoList = aggregate.get(key).stream().map(daily -> CNDailyKHashIndexDto.builder()
                    .code(code).hash(new BigDecimal(key))
                    .leftVacancies(dailyGridKHashKLinesAgg.hashLength() - key.length())
                    .isFillLength(StringUtils.startsWith(key, dailyGridKHashKLinesAgg.getLeftFillChar()))
                    .date(DateUtil.parse(DateUtil.newSimpleFormat("yyyyMMdd").format(daily.get(0).getDate())))
                    .build()).toList();
            result.put(key, kHashIndexDtoList);
        }
        return result;
    }

}
