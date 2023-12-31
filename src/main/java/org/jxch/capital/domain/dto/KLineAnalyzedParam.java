package org.jxch.capital.domain.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KLineAnalyzedParam {
    private Long stockPoolId;
    private String stockCode;
    private Date startDate;
    private Date endDate;

    @Builder.Default
    private DateField dateField = null;
    @Builder.Default
    private int extend = 0;
    @Builder.Default
    private int futureNum = 4;

    public boolean isExtend() {
        return this.extend > 0;
    }

    public Date getExtendStartDate() {
        if (isExtend()) {
            return DateUtil.offset(this.startDate, this.dateField, -this.extend);
        }
        return this.startDate;
    }

    public Date getExtendEndDate() {
        if (isExtend()) {
            return DateUtil.offset(this.endDate, this.dateField, this.extend);
        }
        return this.endDate;
    }

}
