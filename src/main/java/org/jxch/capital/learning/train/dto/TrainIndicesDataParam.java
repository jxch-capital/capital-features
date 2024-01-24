package org.jxch.capital.learning.train.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.learning.train.TrainDataParam;
import org.jxch.capital.support.ServiceWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataParam implements TrainDataParam {
    @Builder.Default
    private KNodeParam kNodeParam = new KNodeParam();
    @Builder.Default
    private Boolean onlyPredictionData = false;
    @Builder.Default
    private Date predictionStartDate = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -10);
    @Builder.Default
    private Date predictionEndDate = Calendar.getInstance().getTime();
    @Builder.Default
    private Boolean simplify = true;
    @Builder.Default
    private List<ServiceWrapper> filterWrappers = new ArrayList<>();
    @Builder.Default
    private ServiceWrapper balancerWrapper = null;

    @Override
    public TrainDataParam setCode(String code) {
        kNodeParam.setCode(code);
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
