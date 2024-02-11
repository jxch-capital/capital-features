package org.jxch.capital.learning.train.param.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.learning.train.param.TrainDataParam;
import org.jxch.capital.support.ServiceWrapper;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataParam implements TrainDataParam {
    @Builder.Default
    private KNodeParam kNodeParam = new KNodeParam().setNormalized(true).setMaxLength(100);
    @Builder.Default
    private Boolean simplify = false;
    @Builder.Default
    private Boolean transpose = false;
    @Builder.Default
    private List<ServiceWrapper> filterWrappers = new ArrayList<>();
    @Builder.Default
    private List<ServiceWrapper> boosterWrappers = new ArrayList<>();
    @Builder.Default
    private List<ServiceWrapper> balancerWrappers = new ArrayList<>();
    @Builder.Default
    private List<ServiceWrapper> scrubberWrappers = new ArrayList<>();
}
