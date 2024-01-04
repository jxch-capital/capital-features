package org.jxch.capital.domain.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignalBackTestParam {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3);

    @Builder.Default
    private Integer futureNum = 4;
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end = Calendar.getInstance().getTime();
    @Builder.Default
    private Integer signalLimitAbs = 5;
}
