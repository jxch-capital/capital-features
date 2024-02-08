package org.jxch.capital.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.train.param.dto.PredictionParam;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Model3SignalParam {
    private List<String> modelNames;
    private PredictionParam predictionParam;
    private Double limitAbs = 0.05;
    private Integer futureNum = 8;
    private Boolean maskContinuousSignals = false;

    @Builder.Default
    private List<String> filters = null;
}
