package org.jxch.capital.domain.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.server.EngineEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class HistoryParam {
    private String code;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -1);

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end = Calendar.getInstance().getTime();

    @Builder.Default
    private String interval = "1d";
    @Builder.Default
    private EngineEnum engine = EngineEnum.YAHOO;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryParam that = (HistoryParam) o;

        if (!code.equals(that.code)) return false;
        if (!start.equals(that.start)) return false;
        if (!end.equals(that.end)) return false;
        if (!interval.equals(that.interval)) return false;
        return engine == that.engine;
    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + interval.hashCode();
        result = 31 * result + engine.hashCode();
        return result;
    }
}
