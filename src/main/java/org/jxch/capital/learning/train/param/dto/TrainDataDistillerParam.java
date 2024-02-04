package org.jxch.capital.learning.train.param.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.train.param.SignalType;
import org.jxch.capital.learning.train.param.TrainDataParam;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainDataDistillerParam implements TrainDataParam {
    private String model;
    private Long trainConfigId;
    @Builder.Default
    private String type = SignalType.UP.toString();
    @Builder.Default
    private Double upTh = 0.8;
    @Builder.Default
    private Double downTh = 0.2;
    @Builder.Default
    private String code = "SPY";
    @Builder.Default
    private Date predictionStartDate = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -10);
    @Builder.Default
    private Date predictionEndDate = Calendar.getInstance().getTime();

    @Override
    public TrainDataParam setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public TrainDataParam setStart(Date start) {
        return setPredictionStartDate(start);
    }

    @Override
    public TrainDataParam setEnd(Date end) {
        return setPredictionEndDate(end);
    }

}
