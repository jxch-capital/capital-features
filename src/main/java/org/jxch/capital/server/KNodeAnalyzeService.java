package org.jxch.capital.server;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.jxch.capital.domain.dto.KLine;

import java.util.Date;
import java.util.List;

public interface KNodeAnalyzeService {

    List<KLine> search(Long stockPoolId, String stockCode, Date startDate, Date endDate);

    default List<KLine> search(Long stockPoolId, String stockCode, Date startDate, Date endDate, DateField dateField, int extend) {
        return search(stockPoolId, stockCode, DateUtil.offset(startDate, dateField, -extend), DateUtil.offset(endDate, dateField, extend));
    }



}
