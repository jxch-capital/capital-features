package org.jxch.capital.learning.model.dto;

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
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PredictionParam {
    @Builder.Default
    private Long trainConfigId = null;
    private String code = "SPY";
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -10);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end = Calendar.getInstance().getTime();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredictionParam that = (PredictionParam) o;
        return Objects.equals(trainConfigId, that.trainConfigId) && Objects.equals(code, that.code) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainConfigId, code, start, end);
    }

}
