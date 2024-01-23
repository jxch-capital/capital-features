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

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataParam implements TrainDataParam {
    private KNodeParam kNodeParam = new KNodeParam();
    private Boolean onlyPredictionData = false;
    private Date predictionStartDate = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -10);
    private Date predictionEndDate = Calendar.getInstance().getTime();
}
