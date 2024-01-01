package org.jxch.capital.server;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import org.jxch.capital.domain.dto.KDashNode;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface RealPricePoolDashboardService {

    List<KDashNode> realDashNodes(Long stockPoolId, Date start);

    default List<KDashNode> realDashNodes(Long stockPoolId, DateField dateField, int offset){
        return realDashNodes(stockPoolId, DateUtil.offset(Calendar.getInstance().getTime(), dateField, -offset));
    }

    default List<KDashNode> realDashNodes(Long stockPoolId, int offsetDays){
        return realDashNodes(stockPoolId, DateField.DAY_OF_YEAR, offsetDays);
    }

}
