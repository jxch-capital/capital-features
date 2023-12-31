package org.jxch.capital.learning.signal.dto;

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
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignalBackTestParam {
    private String code = "SPY";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3);
    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end = Calendar.getInstance().getTime();
    @Builder.Default
    private Integer signalLimitAbs = 5;
    @Builder.Default
    private List<String> filters = null;

    public boolean hasFilter() {
        return Objects.nonNull(filters) && !filters.isEmpty();
    }

}
