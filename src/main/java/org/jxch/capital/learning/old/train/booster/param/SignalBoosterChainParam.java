package org.jxch.capital.learning.old.train.booster.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperGetter;
import org.jxch.capital.support.policy.PolicymakerChainParam;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SignalBoosterChainParam implements PolicymakerChainParam, ServiceWrapperGetter {
    private ServiceWrapper serviceWrapper;
    private List<KNodeTrain> kNodeTrains;
}
