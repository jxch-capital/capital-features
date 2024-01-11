package org.jxch.capital.server;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface StockService {
    List<KLine> history(HistoryParam param);

    default Date getStartOffsetDay(int offset, Date start, String code) {
        List<KLine> history = history(HistoryParam.builder()
                .end(Calendar.getInstance().getTime())
                .start(DateUtil.offset(start, DateField.DAY_OF_YEAR, -Math.max(offset * 10, 200)))
                .code(code)
                .build());
        List<KLine> kLines = history.stream().filter(kLine -> kLine.getDate().getTime() < start.getTime()).toList();
        return kLines.get(kLines.size() - offset).getDate();
    }

}
